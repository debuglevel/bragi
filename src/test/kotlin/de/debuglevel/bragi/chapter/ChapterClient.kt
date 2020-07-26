package de.debuglevel.bragi.chapter

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single
import java.util.*
import javax.validation.constraints.NotBlank

@Client("/chapters")
interface ChapterClient {
    @Get("/{id}")
    fun getOne(@NotBlank id: UUID): Single<GetChapterResponse>

    @Get("/")
    fun getAll(): Set<GetChapterResponse>

    @Post("/")
    fun postOne(@Body chapter: AddChapterRequest): Single<AddChapterResponse>

    @Put("/{id}")
    fun putOne(@NotBlank id: UUID, chapter: UpdateChapterRequest): Single<UpdateChapterResponse>
}