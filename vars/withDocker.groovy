def call(String image, String options, Closure body) {
    docker.image(image).inside(options) {
        body()
    }
}
