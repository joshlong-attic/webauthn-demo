#!/usr/bin/env bash

../spring-security-webauthn/mvnw -DskipTests -f ../spring-security-webauthn/pom.xml clean install
rm -rf target
./mvnw -DskipTests  -Pnative native:compile
./target/passkeys