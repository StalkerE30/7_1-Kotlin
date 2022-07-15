fun mian() {

}

data class Post(
    val id: Int = 0, //идентификатор поста
    val ownerId: Int = 0, // Идентификатор владельца стены, на которой размещена запись.
    val fromId: Int = 0, //идентификтор автора записи
    val created_by: Int = 0, // Идентификатор администратора, который опубликовал запись
    val date: Int = 0, //дата поста
    val text: String, //содержание поста
    val replayOwnerId: Int = 0, // Идентификатор владельца записи, в ответ на которую была оставлена текущая
    val replyPostId: Int = 0, // Идентификатор записи, в ответ на которую была оставлена текущая
    val friends_only: Boolean = false, // если запись была создана с опцией "Только для друзей"
    var comments: Comments? = null, // Информация о комментариях к записи
    val copyright: Copyright? = null, //источник материала
    val likes: Likes = Likes(), // Информация о лайках к записи
    var reposts: Reposts?, // Информация о репостах записи
    var views: Views = Views(), // Информация о просмотрах записи
    val postType: String = "post", // Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
    val signerId: Int = 0, // Идентификатор автора, если запись была опубликована от имени сообщества и подписана пользователем
    val canPin: Boolean = true, // Информация о том, может ли текущий пользователь закрепить запись
    val canDelete: Boolean = true, // Информация о том, может ли текущий пользователь удалить запись
    val canEdit: Boolean = true, // Информация о том, может ли текущий пользователь редактировать запись
    val isPinned: Boolean = false, // Информация о том, что запись закреплена
    var attachments: Array<Attachment> = emptyArray()
)

class Views(
    val count: Int = 0
)

class Reposts(
    val count: Int = 0, // число пользователей, скопировавших запись
    val user_reposted: Boolean = false, // наличие репоста от текущего пользователя
)

class Copyright(
    val id: Int,
    val link: String,
    val name: String,
    val type: String
)

class Comments(
    val count: Int = 0, //количество комментариев
    val can_post: Boolean = true, // информация о том, может ли текущий пользователь комментировать запись
    val groups_can_post: Boolean = true, // информация о том, могут ли сообщества комментировать запись
    val can_close: Boolean = true, // может ли текущий пользователь закрыть комментарии к записи
    val can_open: Boolean = true // может ли текущий пользователь открыть комментарии к записи
)

class Likes(
    val count: Int = 0, //число пользователей, которым понравилась запись
    val user_likes: Boolean = true, // наличие отметки «Мне нравится» от текущего
    val can_like: Boolean = true, // информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
    val can_publish: Boolean = true // информация о том, может ли текущий пользователь сделать репост записи
)

data class Comment(
    val id: Long = 0,
    val fromId: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val replyToUser: Int = 0, // reply_to
    var isDeleted: Boolean = false,
    val postId: Int = 0 // идентификатор поста, к которому относится комментарий
)

class WallService {
    private var posts = emptyArray<Post>()
    private var lastIdPost: Int = 0
    private var comments = emptyArray<Comment>()
    private var lastIdComment: Long = 0

    fun add(post: Post): Post {
        lastIdPost += 1
        posts += post.copy(id = lastIdPost)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        val searchId = post.id
        var find = false
        for ((index, currPost) in posts.withIndex()) {
            if (currPost.id == searchId) {
                posts[index] = post.copy(text = post.text)
                find = true
            }
        }
        return find
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        var comm: Comment? = null
        for ((index, currPost) in posts.withIndex())  {
            if(currPost.id == postId) {
                lastIdComment += 1
                comm = comment.copy(id = lastIdComment, postId = postId)
                comments += comm
            }
        }
        return comm ?: throw PostNotFoundException("Пост с id $postId не найден")
    }

    fun addAttach(post: Post, type: String): Boolean {
        var result = false
        when (type) {
            "Видеозапись" -> {
                post.attachments += VideoAttachment()
                result = true
            }
            "Файл" -> {
                post.attachments += DocAttachment()
                result = true
            }
            "Аудиозапись" -> {
                post.attachments += AudioAttachment()
                result = true
            }
            "Фотография" -> {
                post.attachments += PhotoAttachment()
                result = true
            }
            "Стикер" -> {
                post.attachments += StickerAttachment()
                result = true
            }
        }
        return result
    }
}

class PostNotFoundException(massage:String):RuntimeException(massage)
