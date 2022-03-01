package com.dipesh.onlinegadgetsstore

import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.User
import com.dipesh.onlinegadgetsstore.repository.CartRepository
import com.dipesh.onlinegadgetsstore.repository.UserRepository
import com.dipesh.onlinegadgetsstore.repository.WishListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class UnitTesting {
    private lateinit var userRepository:UserRepository
    private lateinit var cartRepository:CartRepository
    private lateinit var wishRepository:WishListRepository

    @Test
    fun loginTest() = runBlocking {
        userRepository= UserRepository()
        val response =userRepository.checkUser("ram@gmail.com","roshan")
        val expectedResult= true
        val actualResult= response.success
        Assert.assertEquals(expectedResult,actualResult)
    }

    @Test
    fun registerTest()= runBlocking {
        userRepository= UserRepository()
        val user= User(firstName = "Shyam",lastName = "tamang",username = "Shyam", email = "shyam@gmail.com", password = "shyamtamang" )
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun addToCart()= runBlocking {
        userRepository= UserRepository()
        cartRepository= CartRepository()
        ServiceBuilder.token= "Bearer "  + userRepository.checkUser("ram@gmail.com","roshan").token
        val response = cartRepository.addToCart( "621df3da5eccce10a46350ee")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun deleteCartItems()= runBlocking {
        userRepository= UserRepository()
        cartRepository= CartRepository()
        ServiceBuilder.token= "Bearer "  + userRepository.checkUser("ram@gmail.com","roshan").token
        val response =cartRepository.deleteCart("621df3da5eccce10a46350ee")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun addToFavourites()= runBlocking {
        userRepository = UserRepository()
        wishRepository = WishListRepository()
        ServiceBuilder.token = "Bearer " + userRepository.checkUser("ram@gmail.com", "roshan").token
        val response = wishRepository.addtofav("621df3da5eccce10a46350ee")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }


}



