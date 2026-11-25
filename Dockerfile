# A compilacao (GraalVM) acontece no GitHub Actions; aqui so se empacota o executavel nativo.
FROM debian:bookworm-slim

RUN apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --system --no-create-home gateway

WORKDIR /app
COPY target/gateway /app/gateway

USER gateway
EXPOSE 8080
ENTRYPOINT ["/app/gateway"]
