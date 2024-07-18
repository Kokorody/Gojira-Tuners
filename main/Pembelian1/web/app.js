// Mendapatkan elemen-elemen dari DOM
let listProductHTML = document.querySelector('.listProduct');
let listCartHTML = document.querySelector('.listCart');
let iconCart = document.querySelector('.icon-cart');
let iconCartSpan = document.querySelector('.icon-cart span');
let body = document.querySelector('body');
let closeCart = document.querySelector('.close');
let products = [];
let cart = [];

// Event listener untuk toggle tampilan keranjang saat ikon cart diklik
iconCart.addEventListener('click', () => {
    body.classList.toggle('showCart');
});

// Event listener untuk menutup keranjang saat tombol close diklik
closeCart.addEventListener('click', () => {
    body.classList.toggle('showCart');
});

// Fungsi untuk menambahkan data produk ke dalam HTML
const addDataToHTML = () => {
    // Kosongkan konten listProductHTML
    listProductHTML.innerHTML = '';

    // Tambahkan data produk ke dalam HTML
    if (products.length > 0) {
        products.forEach(product => {
            let newProduct = document.createElement('div');
            newProduct.dataset.id = product.id_barang;
            newProduct.classList.add('item');
            newProduct.innerHTML = `
                <img src="data:image/jpeg;base64,${arrayBufferToBase64(product.images)}" alt="">
                <h2>${product.nama_barang}</h2>
                <h4>${product.desc_barang}</h4>
                <div class="price">Rp. ${product.harga_barang}</div>
                <div class="stock">Stock: ${product.stok}</div>
                <button class="addCart">Add To Cart</button>
            `;
            listProductHTML.appendChild(newProduct);
        });
    }
};

// Fungsi untuk mengubah ArrayBuffer menjadi Base64 string
const arrayBufferToBase64 = (buffer) => {
    let binary = '';
    let bytes = new Uint8Array(buffer);
    let len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
};

// Event listener untuk menangani klik tombol "Add To Cart"
listProductHTML.addEventListener('click', (event) => {
    let positionClick = event.target;
    if (positionClick.classList.contains('addCart')) {
        let id_product = positionClick.parentElement.dataset.id;
        addToCart(id_product);
    }
});

// Fungsi untuk menambahkan produk ke dalam keranjang
const addToCart = (product_id) => {
    let positionThisProductInCart = cart.findIndex((value) => value.product_id == product_id);
    let positionProduct = products.findIndex((value) => value.id_barang == product_id);

    if (positionThisProductInCart === -1) {
        // Product not in cart
        if (positionProduct !== -1) {
            // Check if adding 1 exceeds stock
            if (products[positionProduct].stok >= 1) {
                cart.push({
                    product_id: product_id,
                    nama_barang: products[positionProduct].nama_barang,
                    jumlah_beli: 1,
                    harga_satuan: products[positionProduct].harga_barang,
                    total_harga: products[positionProduct].harga_barang
                });
            } else {
                alert('Product stock is insufficient.');
            }
        }
    } else {
        // Product already in cart
        if (positionProduct !== -1) {
            // Check if adding 1 exceeds stock
            if (cart[positionThisProductInCart].jumlah_beli + 1 <= products[positionProduct].stok) {
                cart[positionThisProductInCart].jumlah_beli++;
                cart[positionThisProductInCart].total_harga = cart[positionThisProductInCart].jumlah_beli * cart[positionThisProductInCart].harga_satuan;
            } else {
                alert('Product stock is insufficient.');
            }
        }
    }

    addCartToHTML();
    addCartToMemory();
};

// Fungsi untuk menyimpan keranjang ke dalam localStorage
const addCartToMemory = () => {
    localStorage.setItem('cart', JSON.stringify(cart));
};

// Fungsi untuk menampilkan keranjang belanja ke dalam HTML
const addCartToHTML = () => {
    listCartHTML.innerHTML = '';
    let totalQuantity = 0;
    let totalPrice = 0;

    if (cart.length > 0) {
        cart.forEach(item => {
            totalQuantity += item.jumlah_beli;

            // Temukan informasi produk berdasarkan product_id dari cart
            let positionProduct = products.findIndex((value) => value.id_barang == item.product_id);
            if (positionProduct !== -1) {
                let info = products[positionProduct];

                // Buat elemen baru untuk menampilkan produk dalam keranjang
                let newItem = document.createElement('div');
                newItem.classList.add('item');
                newItem.dataset.id = item.product_id;
                newItem.innerHTML = `
                    <div class="image">
                        <img src="data:image/jpeg;base64,${arrayBufferToBase64(info.images)}">
                    </div>
                    <div class="name">${info.nama_barang}</div>
                    <div class="totalPrice">Rp. ${info.harga_barang * item.jumlah_beli}</div>
                    <div class="quantity">
                        <span class="minus">-</span>
                        <span>${item.jumlah_beli}</span>
                        <span class="plus">+</span>
                    </div>
                `;
                listCartHTML.appendChild(newItem);

                // Hitung total harga produk * quantity untuk total belanja
                totalPrice += info.harga_barang * item.jumlah_beli;
            }
        });
    }

    // Update jumlah total produk di icon keranjang
    iconCartSpan.innerText = totalQuantity;

    // Update tampilan total harga di dalam div 'totalPrice'
    document.getElementById('totalPrice').innerText = Rp. ${totalPrice};
};

// Event listener untuk menangani klik tombol '+' atau '-'
listCartHTML.addEventListener('click', (event) => {
    let positionClick = event.target;
    if (positionClick.classList.contains('minus') || positionClick.classList.contains('plus')) {
        let product_id = positionClick.parentElement.parentElement.dataset.id;
        let type = positionClick.classList.contains('plus') ? 'plus' : 'minus';
        changeQuantityCart(product_id, type);
    }
});

// Fungsi untuk mengubah kuantitas produk dalam keranjang
const changeQuantityCart = (product_id, type) => {
    let positionItemInCart = cart.findIndex((value) => value.product_id == product_id);
    let positionProduct = products.findIndex((value) => value.id_barang == product_id);

    if (positionItemInCart !== -1 && positionProduct !== -1) {
        switch (type) {
            case 'plus':
                // Check if adding 1 exceeds stock
                if (cart[positionItemInCart].jumlah_beli + 1 <= products[positionProduct].stok) {
                    cart[positionItemInCart].jumlah_beli++;
                    cart[positionItemInCart].total_harga = cart[positionItemInCart].jumlah_beli * cart[positionItemInCart].harga_satuan;
                } else {
                    alert('Product stock is insufficient.');
                }
                break;
            case 'minus':
                // Allow decrement even if it goes below 1
                cart[positionItemInCart].jumlah_beli--;
                if (cart[positionItemInCart].jumlah_beli <= 0) {
                    cart.splice(positionItemInCart, 1); // Remove item if quantity is 0 or less
                } else {
                    cart[positionItemInCart].total_harga = cart[positionItemInCart].jumlah_beli * cart[positionItemInCart].harga_satuan;
                }
                break;
            default:
                break;
        }
    }

    addCartToHTML();
    addCartToMemory();
};

// Fungsi untuk memuat data produk dari server saat aplikasi dimulai
const initApp = () => {
    // Ambil data produk dari server
    fetch('/Pembelian1/products')
        .then(response => response.json())
        .then(data => {
            // Simpan data produk ke dalam variabel global products
            products = data;

            // Tambahkan data produk ke dalam HTML
            addDataToHTML();

            // Jika ada data keranjang tersimpan di localStorage, ambil dan tampilkan
            if (localStorage.getItem('cart')) {
                cart = JSON.parse(localStorage.getItem('cart'));
                addCartToHTML();
            }
        })
        .catch(error => console.error('Error fetching products:', error));
};

// Event listener for form inputs to enable/disable the checkout button
document.getElementById('checkoutForm').addEventListener('input', validateForm);

// Function to validate the form and enable/disable the checkout button
function validateForm() {
    const checkoutButton = document.querySelector('.checkOut');
    const form = document.getElementById('checkoutForm');
    const paymentMethod = form.paymentMethod.value;
    const customerFields = ['name', 'email', 'phone', 'address'];

    let allValid = true;

    // Check customer details fields
    customerFields.forEach(field => {
        const input = document.getElementById(field);
        if (!input.value.trim()) {
            allValid = false;
        }
    });

    // Check payment details fields based on the selected payment method
    if (paymentMethod === 'e-wallet') {
        const eWalletFields = ['handphone', 'password'];
        eWalletFields.forEach(field => {
            const input = document.getElementById(field);
            if (!input.value.trim()) {
                allValid = false;
            }
        });
    } else if (paymentMethod === 'bank') {
        const bankFields = ['cardNumber', 'expDate'];
        bankFields.forEach(field => {
            const input = document.getElementById(field);
            if (!input.value.trim()) {
                allValid = false;
            }
        });
    } else {
        allValid = false;
    }

    checkoutButton.disabled = !allValid;
}

// Update payment fields function
function updatePaymentFields() {
    const paymentMethod = document.getElementById('paymentMethod').value;
    const paymentDetails = document.getElementById('paymentDetails');
    paymentDetails.innerHTML = '';

    if (paymentMethod === 'e-wallet') {
        paymentDetails.innerHTML = `
            <label for="handphone">
                <span>Handphone</span>
                <input type="text" name="handphone" id="handphone" required>
            </label>

            <label for="password">
                <span>Password</span>
                <input type="password" name="password" id="password" required>
            </label>
        `;
    } else if (paymentMethod === 'bank') {
        paymentDetails.innerHTML = `
            <label for="cardNumber">
                <span>Card Number</span>
                <input type="text" name="cardNumber" id="cardNumber" required>
            </label>

            <label for="expDate">
                <span>Exp Date</span>
                <input type="text" name="expDate" id="expDate" required>
            </label>
        `;
    }

    validateForm(); // Re-validate the form whenever payment method changes
}

// Panggil fungsi initApp untuk memulai aplikasi
initApp();

//search
function searchItems() {
    let query = document.getElementById('search-bar').value;
    
    fetch(/Pembelian1/search?query=${encodeURIComponent(query)})
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            console.log("Response status:", response.status);
            return response.json();
        })
        .then(data => {
            products = data;
            addDataToHTML();
        })
        .catch(error => console.error('Error fetching search results:', error));
}




//checkout
const checkoutButton = document.querySelector('.checkOut');
document.querySelector('.checkOut').addEventListener('click', (event) => {
    event.preventDefault();

    if (cart.length === 0) {
        alert('Your cart is empty.');
        return;
    }

    // Gather form data
    let formData = new FormData(document.getElementById('checkoutForm'));
    let cartData = JSON.stringify(cart);
    formData.append('cart', cartData);

    // Log to check the data being sent
    console.log({
        name: formData.get('name'),
        email: formData.get('email'),
        phone: formData.get('phone'),
        address: formData.get('address'),
        paymentMethod: formData.get('paymentMethod'),
        cart: formData.get('cart')
    });

    fetch('/Pembelian1/checkout1', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
        alert('Checkout successful: ' + data);
        cart = [];
        localStorage.removeItem('cart');
        addCartToHTML();
        iconCartSpan.innerText = '0';
    })
    .catch(error => {
        alert('Error during checkout: ' + error);
    });
});
