-- 用户表
CREATE TABLE IF NOT EXISTS User (
    uid INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    username VARCHAR(255) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(255) NOT NULL COMMENT '电子邮件',
    deleted INT DEFAULT 0 COMMENT '删除标志，默认值为0'
    ) COMMENT '用户表';

-- 文章表
CREATE TABLE IF NOT EXISTS Article (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    md_text TEXT NOT NULL COMMENT '文章内容，使用Markdown格式',
    title VARCHAR(255) NOT NULL COMMENT '文章标题',
    title_img VARCHAR(255) COMMENT '文章标题图片',
    deleted INT DEFAULT 0 COMMENT '删除标志，默认值为0'
    ) COMMENT '文章表';

-- 文章动态表
CREATE TABLE IF NOT EXISTS ArticleDynamic (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    eye_view INT DEFAULT 0 COMMENT '浏览次数',
    interesting INT DEFAULT 0 COMMENT '感兴趣次数',
    boring INT DEFAULT 0 COMMENT '无趣次数',
    comments_count INT DEFAULT 0 COMMENT '评论次数',
    deleted INT DEFAULT 0 COMMENT '删除标志，默认值为0'
) COMMENT '文章动态表';

-- 评论表
CREATE TABLE IF NOT EXISTS Comment (
   id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
   context TEXT NOT NULL COMMENT '评论内容',
   time DATETIME NOT NULL COMMENT '评论时间',
   parent_id INT COMMENT '父评论ID',
   response_id INT COMMENT '回复评论ID',
   deleted INT DEFAULT 0 COMMENT '删除标志，默认值为0'
) COMMENT '评论表';

-- 用户与评论多对多关联表
CREATE TABLE IF NOT EXISTS User_otm_Comment (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    uid INT NOT NULL COMMENT '用户ID',
    cid INT NOT NULL COMMENT '评论ID',
    FOREIGN KEY (uid) REFERENCES User(uid),
    FOREIGN KEY (cid) REFERENCES Comment(id)
    ) COMMENT '用户与评论关联表';

-- 用户与文章多对多关联表
CREATE TABLE IF NOT EXISTS User_otm_Article (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    uid INT NOT NULL COMMENT '用户ID',
    article_id INT NOT NULL COMMENT '文章ID',
    FOREIGN KEY (uid) REFERENCES User(uid),
    FOREIGN KEY (article_id) REFERENCES Article(id)
    ) COMMENT '用户与文章关联表';

-- 文章与评论多对多关联表
CREATE TABLE IF NOT EXISTS Article_otm_Comment (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    aid INT NOT NULL COMMENT '文章ID',
    comment_id INT NOT NULL COMMENT '评论ID',
    FOREIGN KEY (aid) REFERENCES Article(id),
    FOREIGN KEY (comment_id) REFERENCES Comment(id)
    ) COMMENT '文章与评论关联表';
