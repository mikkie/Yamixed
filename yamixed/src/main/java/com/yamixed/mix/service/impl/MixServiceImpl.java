/**
 * Mix服务类<br>
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yamixed.base.entity.Mix;
import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.base.util.URLUtil;
import com.yamixed.mix.dao.IMixDao;
import com.yamixed.mix.service.IMixService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class MixServiceImpl extends CrudServiceImpl<Mix, IMixDao> implements
		IMixService {

	// 预览图数目
	private static final int IMAGE_SIZE = 20;

	// 每日显示mix数
	private static final int DAY_MIX_SIZE = 30;
	
	//默认编码
	private static final String DEFAULT_CHARSET = "ISO-8859-1";
	
	
	private static final Logger logger = Logger.getLogger(MixServiceImpl.class);

	
	@Override
	@Autowired
	public void setDao(IMixDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean parseMixUrl(Mix mix, String url) {
		//图片
		if(URLUtil.isSimpleImageUrl(url)){
			List<String> imageUrls = new ArrayList<String>();
			imageUrls.add(url);
			mix.setImageUrls(imageUrls);
			return true;
		}
		try {
			Parser parser = new Parser((HttpURLConnection) (new URL(url)).openConnection());
			String charset = parseCharset(mix, parser);
			if(!DEFAULT_CHARSET.equals(charset)){
				parser = new Parser((HttpURLConnection) (new URL(url)).openConnection());
				parser.setEncoding(charset);
			}
			else{
				parser.reset();
			}
			parseTitle(mix, parser);
			parseDesc(mix, parser);
			parseImg(mix, parser);
			return true;
		} catch (ParserException e) {
			logger.error(String.format("解析地址错误%s,%s", url,e.getMessage()));
		} catch (MalformedURLException e) {
			logger.error(String.format("Url地址格式错误%s,%s", url,e.getMessage()));
		} catch (IOException e) {
			logger.error(String.format("IO错误%s,%s", url,e.getMessage()));
		}
		return true;
	}
	
	
	
	
	
	

	/**
	 * 编码解析
	 * 
	 * @param mix
	 * @param parser
	 */
	@SuppressWarnings("serial")
	private String parseCharset(Mix mix, Parser parser) {
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

	/**
	 * 解析图片
	 * 
	 * @param mix
	 * @param parser
	 */
	private void parseImg(Mix mix, Parser parser) {
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
				mix.setImageUrls(new ArrayList<String>(urls));
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析描述
	 * 
	 * @param mix
	 * @param parser
	 */
	private void parseDesc(Mix mix, Parser parser) {
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
						mix.setDescription(curString);
						return;
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析标题
	 * 
	 * @param mix
	 * @param parser
	 */
	private void parseTitle(Mix mix, Parser parser) {
		NodeFilter filter = new NodeClassFilter(TitleTag.class);
		try {
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				TitleTag title_tag = (TitleTag) nodeList.elementAt(0);
				mix.setTitle(title_tag.getTitle());
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Mix> queryDayMix() {
		PageRequest pr = new PageRequest(0, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "createTime"));
		Page<Mix> page = (Page<Mix>) dao.findAll(pr);
		if (page != null) {
			return page.getContent();
		}
		return null;
	}

	@Override
	public void upAndDown(boolean isUp, Long mixId) {
		Mix mix = dao.findOne(mixId);
		if (isUp) {
			mix.setUp(mix.getUp() + 1);
		} else {
			mix.setDown(mix.getDown() + 1);
		}
	}

	@Override
	public List<Mix> queryHotMix() {
		PageRequest pr = new PageRequest(0, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "hit"));
		Page<Mix> page = (Page<Mix>) dao.findAll(pr);
		if (page != null) {
			return page.getContent();
		}
		return null;
	}

	@Override
	public Page<Mix> queryHistoryMix(int pageNum) {
		PageRequest pr = new PageRequest(pageNum, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "createTime"));
		return (Page<Mix>) dao.findAll(pr);
	}

	@Override
	public void addCommentsCount(Long mixId) {
		Mix mix = dao.findOne(mixId);
		if (mix != null) {
			mix.setComments(mix.getComments() + 1);
			dao.save(mix);
		}
	}

	@Override
	public Page<Mix> queryDashBoard(int pageNum) {
		PageRequest pr = new PageRequest(pageNum, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "createTime"));
		return (Page<Mix>) dao.findAll(pr);
	}

	@Override
	public void delMix(Long mixId) {
		dao.delete(mixId);
	}

	@Override
	public void hitMix(Long mixId) {
		Mix mix = dao.findOne(mixId);
		if (mix != null) {
			mix.setHit(mix.getHit() + 1);
		}
	}

	@Override
	public Page<Mix> queryMixByCate(final Long cate, int pageNum) {
		PageRequest pr = new PageRequest(pageNum, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "createTime"));
		Specification<Mix> mixSpec = new Specification<Mix>() {
			
			@Override
			public Predicate toPredicate(Root<Mix> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<Object> catePath = root.get("cate");
				Path<Object> idPath = catePath.get("id");
				query.where(cb.equal(idPath, cate));
				return null;
			}
		};
		return (Page<Mix>) dao.findAll(mixSpec,pr);
	}

}
