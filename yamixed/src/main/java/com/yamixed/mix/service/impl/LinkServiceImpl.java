/**
 * 链接服务<br>
 * ------------------------------------------------------------<br>
 * History<br>
 * ------------------------------------------------------------<br>
 * Legend:<br>
 * 　(+) added feature<br>
 * 　(-) deleted feature<br>
 * 　(#) fixed bug<br>
 * 　(^) upgraded implementation<br>
 *<br>
 * V1.00.00 2012-2-24 limj 新建
 * @author limj
 * @since V1.00.00
 */
package com.yamixed.mix.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.base.util.URLUtil;
import com.yamixed.fav.dao.ILinkDao;
import com.yamixed.fav.entity.Link;
import com.yamixed.mix.service.ILinkService;

/**
 * @author Administrator
 * 
 */

@Service
@Transactional
public class LinkServiceImpl extends CrudServiceImpl<Link, ILinkDao> implements
		ILinkService {

	// 默认编码
	private static final String DEFAULT_CHARSET = "ISO-8859-1";
	
	// 预览图数目
	private static final int IMAGE_SIZE = 12;
	
	
	private static final Logger logger = Logger.getLogger(MixServiceImpl.class);
	

	@Autowired
	@Override
	public void setDao(ILinkDao dao) {
		this.dao = dao;
	}

	@Override
	public Link parseURL(String url) {
		Link link = new Link();
		link.setUrl(url);
		link.setCreateTime(new Date());
		// 图片
		if (URLUtil.isSimpleImageUrl(url)) {
			List<String> imageUrls = new ArrayList<String>();
			imageUrls.add(url);
			link.setImageUrls(imageUrls);
			return link;
		}
		try {
			Parser parser = new Parser(
					(HttpURLConnection) (new URL(url)).openConnection());
			String charset = parseCharset(link, parser);
			if (!DEFAULT_CHARSET.equals(charset)) {
				parser = new Parser(
						(HttpURLConnection) (new URL(url)).openConnection());
				parser.setEncoding(charset);
			} else {
				parser.reset();
			}
			parseTitle(link, parser);
			parseDesc(link, parser);
			parseImg(link, parser);
			return link;
		} catch (ParserException e) {
			logger.error(String.format("解析地址错误%s,%s", url,e.getMessage()));
		} catch (MalformedURLException e) {
			logger.error(String.format("Url地址格式错误%s,%s", url,e.getMessage()));
		} catch (IOException e) {
			logger.error(String.format("IO错误%s,%s", url,e.getMessage()));
		}
		return link;
	}

	private void parseImg(Link link, Parser parser) {
		parser.reset();
		NodeClassFilter metaFilter = new NodeClassFilter(ImageTag.class);
		try {
			NodeList metaList = parser.extractAllNodesThatMatch(metaFilter);
			if (metaList != null && metaList.size() > 0) {
				Set<String> urls = new HashSet<String>();
				for (int i = 0; i < metaList.size(); i++) {
					ImageTag imagTag = (ImageTag) metaList.elementAt(i);
					String imageURL = imagTag.getImageURL();
					if (!StringUtils.isEmpty(imageURL)) {
						urls.add(imageURL);
						if (urls.size() == IMAGE_SIZE) {
							break;
						}
					}
				}
				link.setImageUrls(new ArrayList<String>(urls));
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	private void parseDesc(Link link, Parser parser) {
		parser.reset();
		NodeClassFilter metaFilter = new NodeClassFilter(MetaTag.class);
		try {
			NodeList metaList = parser.extractAllNodesThatMatch(metaFilter);
			if (metaList != null && metaList.size() > 0) {
				Pattern p = Pattern.compile("\\b(description)\\b",
						Pattern.CASE_INSENSITIVE);
				for (int i = 0; i < metaList.size(); i++) {
					MetaTag metaTag = (MetaTag) metaList.elementAt(i);
					String curString = metaTag.getMetaTagName();
					if (curString == null) {
						continue;
					}
					Matcher m = p.matcher(curString); // 正则匹配name是description或keyword的<meta>标签
					if (m.find()) {
						curString = metaTag.getMetaContent();// 提取其content
						link.setDescription(curString);
						return;
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	private void parseTitle(Link link, Parser parser) {
		NodeFilter filter = new NodeClassFilter(TitleTag.class);
		try {
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				TitleTag title_tag = (TitleTag) nodeList.elementAt(0);
				link.setTitle(title_tag.getTitle());
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private String parseCharset(Link link, Parser parser) {
		String charSet = "utf-8";
		try {
			NodeFilter newFilter = new NodeClassFilter() {// 建立一个过滤器 过滤出charset
				public boolean accept(Node node) {
					if (node instanceof MetaTag) {
						MetaTag mt = (MetaTag) node;
						if (mt.getAttribute("http-equiv") != null) {
							if (mt.getAttribute("content").contains("charset")) {
								return true;
							}
						} else if (mt.getAttribute("charset") != null) {
							return true;
						} else {
							return false;
						}
					}
					return false;
				}
			};
			NodeList keywords;
			keywords = parser.extractAllNodesThatMatch(newFilter);
			for (int i = 0; i < keywords.size(); i++) {
				MetaTag mt = (MetaTag) keywords.elementAt(i);
				if (mt.getAttribute("content") != null) {
					String charset = mt.getAttribute("content").toLowerCase()
							.split("charset=")[1];
					charSet = charset;
				} else {
					String charset = mt.getAttribute("charset");
					charSet = charset;
				}
			}
			return charSet;
		} catch (ParserException e) {
			e.printStackTrace();
			return charSet;
		}
	}

	
	
	@Override
	public void upAndDown(boolean isUp, Long linkId) {
		Link link = dao.findOne(linkId);
		if (isUp) {
			link.setUp(link.getUp() + 1);
		} else {
			link.setDown(link.getDown() + 1);
		}
	}

}
