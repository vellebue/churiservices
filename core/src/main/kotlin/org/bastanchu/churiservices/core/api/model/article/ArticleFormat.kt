package org.bastanchu.churiservices.core.api.model.article

data class ArticleFormat(val formatId : String,
                         val description : String,
                         val minUnit : Boolean,
                         val saleUnit : Boolean,
                         val conversionFactor : Double) {
}