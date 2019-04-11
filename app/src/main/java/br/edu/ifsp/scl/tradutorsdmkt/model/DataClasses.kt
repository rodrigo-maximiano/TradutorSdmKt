package br.edu.ifsp.scl.tradutorsdmkt.model

data class Resposta(
    var metadata: Metadata? = Metadata(),var results: List<Result?>? = listOf()
)
data class LanguagesResponse(
    var metadata: Metadata? = Metadata(), var results: List<Language?>? = listOf()
)
data class Language(
    var region: String? = "",
    var source: String? = "",
    var sourceLanguage: SourceLanguage? = SourceLanguage(),
    var type: String? = ""
)
data class SourceLanguage(
    var id: String? = "",
    var language: String? = ""
)
data class Result(
    var id: String? = "",
    var language: String? = "",
    var lexicalEntries: List<LexicalEntry?>? = listOf(),
    var type: String? = "",
    var word: String? = ""
)
data class LexicalEntry(
    var entries: List<Entry?>? = listOf(),
    var language: String? = "",
    var lexicalCategory: String? = "",
    var text: String? = ""
)
data class Entry(
    var grammaticalFeatures: List<GrammaticalFeature?>? = listOf(),
    var homographNumber: String? = "",
    var senses: List<Sense?>? = listOf()
)
data class Sense(
    var examples: List<Example?>? = listOf(),
    var id: String? = "",
    var notes: List<Note?>? = listOf(),
    var translations: List<Translation?>? = listOf()
)
data class Note(
    var text: String? = "",
    var type: String? = ""
)
data class Example(
    var text: String? = "",
    var translations: List<Translation?>? = listOf()
)
data class Translation(
    var language: String? = "",
    var text: String? = ""
)
data class GrammaticalFeature(
    var text: String? = "",
    var type: String? = ""
)
data class Metadata(
    var provider: String? = ""
)