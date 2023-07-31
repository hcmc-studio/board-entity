package studio.hcmc.board.entity

import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import studio.hcmc.board.domain.ArticleDomain
import studio.hcmc.board.dto.ArticleDTO
import studio.hcmc.board.table.ArticleTable
import studio.hcmc.board.vo.ArticleVO
import studio.hcmc.kotlin.protocol.DataTransferObjectConsumer
import studio.hcmc.kotlin.protocol.QualifiedValueObjectConvertor
import studio.hcmc.kotlin.protocol.ValueObjectConverter

class ArticleEntity(id: EntityID<Long>) :
    LongEntity(id),
    ArticleDomain<EntityID<Long>, Long>,
    ValueObjectConverter<ArticleVO>,
    QualifiedValueObjectConvertor<ArticleVO.Qualified>,
    DataTransferObjectConsumer<ArticleDTO>
{
    companion object : LongEntityClass<ArticleEntity>(ArticleTable)

    override var boardId by ArticleTable.boardId
    override var title by ArticleTable.title
    override var body by ArticleTable.body
    override var writerNickname by ArticleTable.writerNickname
    override var writerPassword by ArticleTable.writerPassword
    override var writerAddress by ArticleTable.writerAddress
    override val writtenAt by ArticleTable.writtenAt
    override var lastModifiedAt by ArticleTable.lastModifiedAt

    val board by BoardEntity referencedOn ArticleTable.boardId

    override fun toValueObject() = ArticleVO(
        id = id.value,
        boardId = boardId,
        title = title,
        body = body,
        writerNickname = writerNickname,
        writerPassword = writerPassword,
        writerAddress = writerAddress,
        writtenAt = writtenAt,
        lastModifiedAt = lastModifiedAt
    )

    override fun toQualifiedValueObject() = ArticleVO.Qualified(
        id = id.value,
        boardId = boardId,
        board = board.toQualifiedValueObject(),
        title = title,
        body = body,
        writerNickname = writerNickname,
        writerPassword = writerPassword,
        writerAddress = writerAddress,
        writtenAt = writtenAt,
        lastModifiedAt = lastModifiedAt
    )

    override fun fromDataTransferObject(dto: ArticleDTO) {
        when (dto) {
            is ArticleDTO.Post -> fromDataTransferObject(dto)
            is ArticleDTO.Put -> fromDataTransferObject(dto)
        }
    }

    private fun fromDataTransferObject(dto: ArticleDTO.Post) {
        title = dto.title
        body = dto.body
        writerNickname = dto.writerNickname
        writerPassword = dto.writerPassword
    }

    private fun fromDataTransferObject(dto: ArticleDTO.Put) {
        title = dto.title
        body = dto.body
        lastModifiedAt = Clock.System.now()
    }
}