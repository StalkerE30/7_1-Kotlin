interface Attachment {
    val type: String
}

data class VideoAttachment(override val type: String = "video",
                           val video: Video = Video()) : Attachment
class Video(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val description: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val duration: Int = 0// длительность в секундах
)

data class DocAttachment(override val type: String = "Doc",
                         val doc: Docs = Docs()) : Attachment

class Docs(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val size: Int = 0,
    val ext: String = "docx"
)

data class AudioAttachment(override val type: String = "Audio",
                           val audio: Audio = Audio()) : Attachment


class Audio(
    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String = "",
    val title: String = "",
    val duration: Int = 0, // длительность в секундах
    val url: String = "",
    val albumID: Int = 0,
    val genreId: Int =0
)

data class PhotoAttachment(override val type: String = "Photo",
                           val photo: Photo = Photo()) : Attachment

class Photo(
    val id: Int = 0,
    val albumId: Int = 0,
    val ownerId: Int = 0,
    val userId: Int = 0,
    val text: String = "",
    val date: Int = 0,
    val width: Int = 0,
    val height: Int = 0
)

data class StickerAttachment(override val type: String = "Sticker",
                             val sticker: Sticker = Sticker()) : Attachment

class Sticker(
    val productId: Int = 0,
    val stickerId: Int = 0,
    val images: Array<Images> = emptyArray(),
    val animationUrl: String = "",
    val isAllowed: Boolean = true
)

data class Images(
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
)

//class Thumb(
//    val id: Int =0,
//    val thumb256: String = "",
//    val thumb96: String = "",
//    val thumb48: String = ""
//)