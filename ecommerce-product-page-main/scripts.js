function picSelect(thumb) {
    if (thumb == t2) {
        document.getElementById('displayed').src = 'images/image-product-2.jpg';
        nail2.style.opacity = '40%';
        nail2.style.border = '4px solid hsl(26, 100%, 55%)';
        nail2.style.border.radius = '15%';
        nail1.style.border = '4px solid transparent';
        nail1.style.opacity = '100%';
        nail3.style.border = '4px solid transparent';
        nail3.style.opacity = '100%';
        nail4.style.border = '4px solid transparent';
        nail4.style.opacity = '100%';
    }
    else if (thumb == t3) {
        document.getElementById('displayed').src = 'images/image-product-3.jpg';
        nail3.style.opacity = '40%';
        nail3.style.border = '4px solid hsl(26, 100%, 55%)';
        nail3.style.border.radius = '15%';
        nail1.style.border = '4px solid transparent';
        nail1.style.opacity = '100%';
        nail2.style.border = '4px solid transparent';
        nail2.style.opacity = '100%';
        nail4.style.border = '4px solid transparent';
        nail4.style.opacity = '100%';
    }
    else if (thumb == t4) {
        document.getElementById('displayed').src = 'images/image-product-4.jpg';
        nail4.style.opacity = '40%';
        nail4.style.border = '4px solid hsl(26, 100%, 55%)';
        nail4.style.border.radius = '15%';
        nail1.style.border = '4px solid transparent';
        nail1.style.opacity = '100%';
        nail3.style.border = '4px solid transparent';
        nail3.style.opacity = '100%';
        nail2.style.border = '4px solid transparent';
        nail2.style.opacity = '100%';
    }
    else {
        document.getElementById('displayed').src = 'images/image-product-1.jpg';
        nail1.style.opacity = '40%';
        nail1.style.border = '4px solid hsl(26, 100%, 55%)';
        nail1.style.border.radius = '15%';
        nail2.style.border = '4px solid transparent';
        nail2.style.opacity = '100%';
        nail3.style.border = '4px solid transparent';
        nail3.style.opacity = '100%';
        nail4.style.border = '4px solid transparent';
        nail4.style.opacity = '100%';
    }
}