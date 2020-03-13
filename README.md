# IF3210-2020-56-BolaSepak

Fitur utama dari BolaSepak adalah memberikan schedule pertandingan sepak bola yang akan datang, dan juga yang sudah lewat. Selain schedule BolaSepak juga perlu menunjukkan lokasi pertandingan sepak bola dan juga cuaca di lokasi pertandingan tersebut. Selain itu pengguna juga dapat melihat profil sebuah tim beserta pertandingan yang pernah dijalani. Pengguna dapat subscribe pada sebuah tim dan akan mendapatkan notifikasi ketika tim tersebut mengikuti pertandingan baru. Selain itu, untuk mendorong asisten mata kuliah IF3210 agar lebih sering berolahraga, aplikasi BolaSepak juga memiliki sebuah step counter untuk menghitung berapa langkah yang telah diambil asisten yang direset setiap harinya.

Berikut adalah spesifikasi yang harus dipenuhi oleh aplikasi BolaSepak:
- Pengguna dapat melihat schedule pertandingan sepak bola, dan cuaca di lokasi pertandingan tersebut.
- Pengguna dapat melihat informasi pertandingan yang sudah/belum diadakan. Informasi yang wajib ditampilkan adalah logo masing-masing tim dan tanggal pertandingan.
- Untuk pertandingan yang sudah diadakan, informasi tambahan yang wajib ditampilkan adalah skor, jumlah tembakan untuk tim home dan away, dan pencetak gol dan juga menit tercetaknya gol untuk tim home dan away. 
- Untuk pertandingan yang belum diadakan, informasi tambahan yang wajib ditampilkan adalah cuaca di lokasi pertandingan, pada jam pertandingan berlangsung.
- Bila perangkat tidak terkoneksi internet, aplikasi harus dapat menampilkan data pertandingan yang diambil dari cache.
- Pengguna dapat subscribe pada sebuah tim sepakbola.
- Pengguna dapat menerima notifikasi jika tim sepakbola yang di subscribe akan bertanding.
- Pengguna dapat melakukan pencarian pertandingan berdasarkan nama tim. Pencarian harus dilakukan secara real-time. (Hasil berubah seiring pengguna memasukkan kata kunci pencarian).
- Pengguna dapat melihat jumlah langkah yang telah dilakukan setiap harinya. Langkah pengguna tetap terhitung walaupun aplikasi tidak terbuka. (hint: gunakan service)

Berikut beberapa batasan tambahan untuk spesifikasi di atas:
- Operasi seperti mendapatkan daftar pertandingan, dan cuaca dilakukan dengan menggunakan API TheSportDB, OpenWeather dan Google Maps Geofencing.
- Caching dilakukan dengan menggunakan sqlite. Dibebaskan untuk menggunakan library apapun untuk membaca atau menuliskan data pada sqlite.
- Aplikasi akan menampilkan daftar pertandingan dalam dua kolom ketika device pengguna berada pada orientasi landscape, dan dalam satu kolom ketika device pengguna berada dalam orientasi vertical.
- Pada laman Team Detail terdapat dua buah tab yang: SEKARANG dan SEBELUM. Gunakanlah Fragment untuk mengimplementasi view pada setiap tab di atas.

# Cara Kerja Aplikasi
Aplikasi dibuat dengan Andoroid Studio dengan JAVA sebagai bahasa pemrograman. Aplikasi akan mengambill data pertandingan yang diambil dari API TheSportDB,
laporan cuaca dengan menggunakan API OpenWeather, dan juga track step menggunakan API Google Maps Geofencing.
Library yang digunakan untuk mengambil data API adalah dengan library Volley dan Retrofit.
Pengguna dapat melihat halaman HomePage yang berisi jadwal pertandingan dan jumlah langkah saat pertama kali membuka aplikasi.
Pengguna dapat menekan sebuah pertandingan untuk menunjukkan detail pertandingan berupa Tanggal, Skor, Cuaca, Logo, dan detail mengenai tembakan.
Pengguna juga dapat melihat detail tim untuk melihat 2 buah tab "SEBELUM" dan "SEKARANG", dan juga ada tombol subscribe

# Library yang digunakan
- Retrofit : Mengambil JSON API theSportDB, digunakan untuk mengubah JSON menjadi Class Object Java
- Volley : Mengambil JSON API theSportDB, digunakan untuk fetch JSON
- Picasso : Untuk Loading Image logo
- Material UI: Mempermudah pembuatan UI
- joda-time : compare date (mengecek beda hari) tidak peduli time untuk reset stepCounter service

# Screenshot Aplikasi