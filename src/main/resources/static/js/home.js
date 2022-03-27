document.addEventListener('alpine:init', () => {
    Alpine.data('slider', () => ({
        currentIndex: 1,
        images: [
            'https://source.unsplash.com/1600x900/?beach',
            'https://source.unsplash.com/1600x900/?cat',
            'https://source.unsplash.com/1600x900/?dog',
            'https://source.unsplash.com/1600x900/?lego',
            'https://source.unsplash.com/1600x900/?textures&patterns'
        ],
        back() {
            if (this.currentIndex > 1) {
                this.currentIndex = this.currentIndex - 1;
            }
        },
        next() {
            if (this.currentIndex < this.images.length) {
                this.currentIndex = this.currentIndex + 1;
            } else if (this.currentIndex <= this.images.length) {
                this.currentIndex = this.images.length - this.currentIndex + 1
            }
        },
    }))
})

$(document).ready(function () {
    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 10,
        responsiveClass: true,
        responsive: {
            0: {
                items: 1,
                nav: true
            },
            600: {
                items: 3,
                nav: false
            },
            1000: {
                items: 5,
                nav: true,
                loop: false
            }
        }
    })
});