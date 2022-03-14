package com.cherish.randomfactproject.utils

import com.cherish.randomfactproject.data.model.Rating
import com.cherish.randomfactproject.data.model.StoreResponse

object TestUtils {
    val list = listOf(
        StoreResponse(
        1,
        "category",
        "description",
        2,"https://i.pravatar.cc",
        2.0,
        Rating(2,3.8),
        "test",
    )
    )
}