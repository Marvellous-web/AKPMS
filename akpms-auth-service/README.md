# README #


### What is this repository for? ###

AKPMS Auth server

[Ant [pattern='/oauth/token'], Ant [pattern='/oauth/token_key'], Ant [pattern='/oauth/check_token']]

{{AUTH_SERVICE}}/actuator/health

### Generate public and private keys: ### 

OpenSSL> genrsa -out jwt.pem 2048 (This will generate keys)
OpenSSL> rsa -in jwt.pem (This will fetch private key)
OpenSSL> rsa -in jwt.pem -pubout (This will fetch public key)

OpenSSL can be used from gitbash or (C:\Program Files\Git\usr\bin\openssl.exe)

-----------------------------
# References

https://www.youtube.com/watch?v=l9chhjL7Kuk&feature=youtu.be
https://github.com/talk2amareswaran/Spring-Boot-2.1-OAuth2-Authorization-Server-and-Resource-Server-JWT-and-MySQL



