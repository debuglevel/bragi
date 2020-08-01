package de.debuglevel.bragi.character

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single
import java.util.*
import javax.validation.constraints.NotBlank

@Client("/characters")
interface CharacterClient {
    @Get("/{id}")
    fun getOne(@NotBlank id: UUID): Single<GetCharacterResponse>

    @Get("/")
    fun getAll(): Set<GetCharacterResponse>

    @Post("/")
    fun postOne(@Body character: AddCharacterRequest): Single<AddCharacterResponse>

    @Put("/{id}")
    fun putOne(@NotBlank id: UUID, character: UpdateCharacterRequest): Single<UpdateCharacterResponse>
}