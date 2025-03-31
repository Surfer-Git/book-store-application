console.log("Inside Product.js");
document.addEventListener('alpine:init', () => {
        Alpine.data('productInit', () => ({
            count: 0,
            increase() {
                this.count++;
                console.log('count: ', this.count);
            }
        }));
    })