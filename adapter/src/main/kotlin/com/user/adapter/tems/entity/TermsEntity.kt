package com.user.adapter.tems.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.terms.Terms
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "terms")
data class TermsEntity(

    @Column
    val sequence: Long,

    @Column
    val content: String,

    @Column
    val isMandatory: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long,
) : BaseEntity() {

    fun toTerms(): Terms {
        return Terms(
            sequence = sequence,
            content = content,
            isMandatory = isMandatory,
            termsId = id,
        )
    }
}