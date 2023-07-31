package studio.hcmc.board.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import studio.hcmc.board.domain.BoardDomain
import studio.hcmc.board.dto.BoardDTO
import studio.hcmc.board.table.BoardTable
import studio.hcmc.board.vo.BoardVO
import studio.hcmc.kotlin.protocol.DataTransferObjectConsumer
import studio.hcmc.kotlin.protocol.QualifiedValueObjectConvertor
import studio.hcmc.kotlin.protocol.ValueObjectConverter

class BoardEntity(id: EntityID<Long>) :
    LongEntity(id),
    BoardDomain<EntityID<Long>>,
    ValueObjectConverter<BoardVO>,
    QualifiedValueObjectConvertor<BoardVO.Qualified>,
    DataTransferObjectConsumer<BoardDTO>
{
    companion object : LongEntityClass<BoardEntity>(BoardTable)

    override var name by BoardTable.name

    override fun toValueObject() = BoardVO(
        id = id.value,
        name = name
    )

    override fun toQualifiedValueObject() = BoardVO.Qualified(
        id = id.value,
        name = name
    )

    override fun fromDataTransferObject(dto: BoardDTO) {
        when (dto) {
            is BoardDTO.Post -> fromDataTransferObject(dto)
            is BoardDTO.Put -> fromDataTransferObject(dto)
        }
    }

    private fun fromDataTransferObject(dto: BoardDTO.Post) {
        name = dto.name
    }

    private fun fromDataTransferObject(dto: BoardDTO.Put) {
        name = dto.name
    }
}