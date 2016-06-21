package com.uikoo9.manage.blog.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.uikoo9.util.core.annotation.QTable;
import com.uikoo9.util.core.data.QStringUtil;

/**
 * BlogCommentModel<br>
 * id						id<br>
 * blog_id					博客id<br>
 * blog_comment_uname		博客评论昵称<br>
 * blog_comment_content		博客评论内容<br>
 * blog_comment_parent_id	博客评论父id<br>
 * cdate					创建时间<br>
 * cuser_id					创建人id<br>
 * cuser_name				创建人姓名<br>
 * @author qiaowenbin
 */
@QTable("t_blog_comment")
@SuppressWarnings("serial")
public class BlogCommentModel extends Model<BlogCommentModel>{
	
	public static final BlogCommentModel dao = new BlogCommentModel();
	
	/**
	 * find all
	 * @return
	 */
	public List<BlogCommentModel> findAll(){
		return findAll(null);
	}
	
	/**
	 * find all by order
	 * @param order
	 * @return
	 */
	public List<BlogCommentModel> findAll(String order){
		StringBuilder sb = new StringBuilder("select * from t_blog_comment ");
		if(QStringUtil.isEmpty(order)){
			return dao.find(sb.append("order by id desc").toString());
		}else{
			return dao.find(sb.append(order).toString());
		}
	}
	
	/**
	 * 获取对应的blog
	 * @return
	 */
	public BlogArticleModel blog(){
		return BlogArticleModel.dao.findById(get("blog_id"));
	}
	
	/**
	 * get child comments
	 * @return
	 */
	public List<BlogCommentModel> comments(){
		return BlogCommentModel.dao.find("select tbc.*,tuu.ucenter_user_nickname from t_blog_comment tbc,t_ucenter_user tuu where tbc.cuser_id=tuu.id and tbc.blog_id=? and tbc.blog_comment_parent_id=? order by tbc.cdate desc", get("blog_id"), get("id"));
	}
	
}
