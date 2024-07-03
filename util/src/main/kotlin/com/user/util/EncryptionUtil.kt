package com.user.util

import org.springframework.beans.factory.annotation.Value
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private const val PBKDF2 = "PBKDF2WithHmacSHA256"
    private const val AES_GCM_NO_PADDING = "AES/GCM/NoPadding"
    private const val ITERATION_CREATION = 65536
    private const val KEY_LENGTH = 256
    private const val AES = "AES"

    private val ivBytes: ByteArray
    private val secretKeySpec: SecretKeySpec

    @Value("\${encryption.secret-key}")
    private lateinit var encryptionSecretKey: String

    @Value("\${encryption.salt}")
    private lateinit var salt: String

    init {
        val factory = SecretKeyFactory.getInstance(PBKDF2)
        val spec = PBEKeySpec(encryptionSecretKey.toCharArray(), salt.toByteArray(), ITERATION_CREATION, KEY_LENGTH)
        val secretKey = factory.generateSecret(spec)
        secretKeySpec = SecretKeySpec(secretKey.encoded, AES)

        ivBytes = ByteArray(12)
        SecureRandom().nextBytes(ivBytes)
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(AES_GCM_NO_PADDING)
        val spec = GCMParameterSpec(128, ivBytes)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, spec)
        val encrypted = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(input: String): String {
        val cipher = Cipher.getInstance(AES_GCM_NO_PADDING)
        val spec = GCMParameterSpec(128, ivBytes)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, spec)
        return String(cipher.doFinal(Base64.getDecoder().decode(input)))
    }
}
