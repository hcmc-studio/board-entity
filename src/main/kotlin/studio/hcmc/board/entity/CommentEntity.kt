package studio.hcmc.board.entity

import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import studio.hcmc.board.domain.CommentDomain
import studio.hcmc.board.dto.CommentDTO
import studio.hcmc.board.table.CommentTable
import studio.hcmc.board.vo.CommentVO
import studio.hcmc.kotlin.protocol.DataTransferObjectConsumer
import studio.hcmc.kotlin.protocol.QualifiedValueObjectConvertor
import studio.hcmc.kotlin.protocol.ValueObjectConverter

class CommentEntity(id: EntityID<Long>) :
    LongEntity(id),
    CommentDomain<EntityID<Long>, Long>,
    ValueObjectConverter<CommentVO>,
    QualifiedValueObjectConvertor<CommentVO.Qualified>,
    DataTransferObjectConsumer<CommentDTO>
{
    companion object : LongEntityClass<CommentEntity>(CommentTable)

    override var articleId by CommentTable.articleId
    override var body by CommentTable.body
    override var writerNickname by CommentTable.writerNickname
    override var writerPassword by CommentTable.writerPassword
    override var writerAddress by CommentTable.writerAddress
    override val writtenAt by CommentTable.writtenAt
    override var lastModifiedAt by CommentTable.lastModifiedAt

    val article by ArticleEntity referencedOn CommentTable.articleId

    override fun toValueObject() = CommentVO(
        id = id.value,
        articleId = articleId,
        body = body,
        writerNickname = writerNickname,
        writerPassword = writerPassword,
        writerAddress = writerAddress,
        writtenAt = writtenAt,
        lastModifiedAt = lastModifiedAt
    )

    override fun toQualifiedValueObject() = CommentVO.Qualified(
        id = id.value,
        articleId = articleId,
        article = article.toQualifiedValueObject(),
        body = body,
        writerNickname = writerNickname,
        writerPassword = writerPassword,
        writerAddress = writerAddress,
        writtenAt = writtenAt,
        lastModifiedAt = lastModifiedAt
    )

    override fun fromDataTransferObject(dto: CommentDTO) {
        when (dto) {
            is CommentDTO.Post -> fromDataTransferObject(dto)
            is CommentDTO.Put -> fromDataTransferObject(dto)
        }
    }

    private fun fromDataTransferObject(dto: CommentDTO.Post) {
        body = dto.body
        writerNickname = dto.writerNickname
        writerPassword = dto.writerPassword
        writerAddress = dto.writerAddress
    }

    private fun fromDataTransferObject(dto: CommentDTO.Put) {
        body = dto.body
        lastModifiedAt = Clock.System.now()
    }
}