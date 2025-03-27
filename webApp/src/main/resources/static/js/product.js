console.log('product.js loaded');
try {
    document.addEventListener('alpine:init', () => {
        console.log('alpine:init fired');
        Alpine.data('productInit', () => ({
            count: 0,
            increase() {
                this.count++;
                console.log('count increased:', this.count);
            }
        }))
    });
} catch (error) {
    console.error('Error in Alpine.js initialization:', error);
}