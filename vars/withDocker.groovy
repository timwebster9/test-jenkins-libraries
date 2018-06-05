def call(String image, Closure body) {
    docker.image(image).inside {
        body()
    }
}
