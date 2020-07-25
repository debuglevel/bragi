package de.debuglevel.bragi.chapter

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single
import java.util.*
import javax.validation.constraints.NotBlank

@Client("/chapters")
interface ChapterClient {
    @Get("/{id}")
    fun getOne(@NotBlank id: UUID): Single<UpdateChapterResponse>

    @Post("/")
    fun postOne(@Body chapter: AddChapterRequest): Single<UpdateChapterResponse>

    @Get("/")
    fun getAll(): Set<AddChapterRequest>
}