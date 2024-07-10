以下是 `UserController`、`CommentController` 和 `ArticleController` 类中定义的 REST API 接口的汇总

### UserController

- **POST /user/login**
    - 用户登录接口。
    - 请求体：JSON 对象，包含 `username` 和 `password`。
    - 响应：如果登录成功，返回令牌；否则返回 "login unsuccessfully"。

- **POST /user/register**
    - 用户注册接口。
    - 请求体：用户对象。
    - 响应：注册成功返回 "Register successfully"，重复用户名返回 "Repeated username"。

- **GET /user/getAllUser**
    - 获取所有用户列表的接口。

- **GET /user/deleteUser**
    - 删除当前认证用户的接口。

- **GET /user/hello**
    - 简单的接口，返回 "Hello world!"。

- **POST /user/changePassword**
    - 修改用户密码的接口。
    - 请求体：JSON 对象，包含 `username` 和 `newPassword`。
    - 响应：布尔值，表示操作成功与否。

- **GET /user/getUser**
    - 获取当前认证用户的详细信息的接口。

- **GET /user/getOtherUser**
    - 获取其他用户信息的接口，需要提供 `other_uid` 参数。

### CommentController

- **GET /comment/getAllArticleComments**
    - 获取指定文章所有评论的接口。
    - 参数：`articleId`。

- **POST /comment/addComment**
    - 添加评论的接口。
    - 请求体：JSON 对象，包含评论信息和 `articleId`。
    - 响应：布尔值，表示评论是否添加成功。

- **GET /comment/deleteComment**
    - 删除评论的接口。
    - 参数：`commentId`，当前认证用户的身份通过令牌验证。

### ArticleController

- **GET /article/tsetToken**
    - 用于测试令牌验证的接口。
    - 参数：`articleId`。

- **GET /article/getArticle**
    - 获取指定文章的详细信息的接口。
    - 参数：`articleId`。

- **POST /article/addArticle**
    - 添加文章的接口。
    - 请求体：文章对象。
    - 响应：布尔值，表示文章是否添加成功。

- **GET /article/deleteArticle**
    - 删除文章的接口。
    - 参数：`id`，当前认证用户的身份通过令牌验证。

- **POST /article/updateArticle**
    - 更新文章的接口。
    - 请求体：文章对象。
    - 响应：布尔值，表示文章是否更新成功。

- **GET /article/getUserAllSummaryArticles**
    - 获取用户发布的所有文章的摘要信息的接口。
    - 参数：`uid`。

- **GET /article/searchArticle**
    - 搜索文章的接口。
    - 参数：`search`，搜索关键词。

- **GET /article/getArticledynamic**
    - 获取文章动态信息的接口。
    - 参数：`articleId`。

- **GET /article/addInteresting**
    - 对文章点赞的接口。
    - 参数：`articleId`。

- **GET /article/addBoring**
    - 对文章点踩的接口。
    - 参数：`articleId`。

这些接口涵盖了用户管理、评论管理和文章管理的基本功能。