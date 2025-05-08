package com.mycompany.p_uts_23090170_a_2025;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;

public class CRUD_23090170_A_2025 {

    public static void main(String[] args) {
        // Alamat koneksi MongoDB lokal
        String uri = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Koneksi ke database
            MongoDatabase database = mongoClient.getDatabase("uts_23090170_A2025");
            MongoCollection<Document> collection = database.getCollection("coll_23090170_A_2025");

            Scanner scanner = new Scanner(System.in);
            int choice;

            // Menu utama
            do {
                System.out.println("\n=== MENU CRUD MONGODB ===");
                System.out.println("1. Create (Tambah Data)");
                System.out.println("2. Read (Tampilkan Semua Data)");
                System.out.println("3. Update (Ubah Semua Data)");
                System.out.println("4. Delete (Hapus Data)");
                System.out.println("5. Search (Cari Berdasarkan Nama)");
                System.out.println("0. Exit");
                System.out.print("Pilihan Anda: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // buang newline agar input tidak terlewat

                switch (choice) {
                    case 1 -> {
                        // CREATE
                        System.out.print("Nama: ");
                        String name = scanner.nextLine();
                        System.out.print("Prodi: ");
                        String prodi = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Umur: ");
                        int age = scanner.nextInt();

                        Document mahasiswa = new Document("name", name)
                                .append("prodi", prodi)
                                .append("email", email)
                                .append("age", age);
                        collection.insertOne(mahasiswa);
                        System.out.println("✅ Data berhasil ditambahkan!");
                    }

                    case 2 -> {
                        // READ
                        System.out.println("=== Data Mahasiswa ===");
                        FindIterable<Document> allData = collection.find();
                        for (Document d : allData) {
                            System.out.println(d.toJson());
                        }
                    }

                    case 3 -> {
                        // UPDATE
                        System.out.print("Masukkan nama yang ingin diupdate: ");
                        String oldName = scanner.nextLine();

                        System.out.print("Nama Baru: ");
                        String newName = scanner.nextLine();
                        System.out.print("Prodi Baru: ");
                        String newProdi = scanner.nextLine();
                        System.out.print("Email Baru: ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Umur Baru: ");
                        int newAge = scanner.nextInt();
                        scanner.nextLine();

                        Bson filter = Filters.eq("name", oldName);
                        Bson updates = Updates.combine(
                                Updates.set("name", newName),
                                Updates.set("prodi", newProdi),
                                Updates.set("email", newEmail),
                                Updates.set("age", newAge)
                        );

                        collection.updateOne(filter, updates);
                        System.out.println("✅ Data berhasil diupdate!");
                    }

                    case 4 -> {
                        // DELETE
                        System.out.print("Masukkan nama yang ingin dihapus: ");
                        String name = scanner.nextLine();
                        Bson filter = Filters.eq("name", name);
                        collection.deleteOne(filter);
                        System.out.println("🗑️ Data berhasil dihapus!");
                    }

                    case 5 -> {
                        // SEARCH berdasarkan nama
                        System.out.print("Masukkan nama yang ingin dicari: ");
                        String searchName = scanner.nextLine();
                        Bson searchFilter = Filters.eq("name", searchName);
                        FindIterable<Document> result = collection.find(searchFilter);

                        System.out.println("=== Hasil Pencarian ===");
                        for (Document d : result) {
                            System.out.println(d.toJson());
                        }
                    }

                    case 0 -> System.out.println("🚪 Keluar dari program. Terima kasih.");
                    default -> System.out.println("❌ Pilihan tidak valid. Silakan ulangi.");
                }

            } while (choice != 0);

        } catch (Exception e) {
            System.out.println("❗ Terjadi kesalahan: " + e.getMessage());
        }
    }
}
