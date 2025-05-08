/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.p_uts_23090170_a_2025;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Lenovo
 */
public class CRUD_23090170_A_2025 {

    public static void main(String[] args) {
        // Koneksi ke MongoDB (pastikan MongoDB aktif di localhost:27017)
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // Pilih database dan collection
            MongoDatabase database = mongoClient.getDatabase("uts_23090170_A2025");
            MongoCollection<Document> collection = database.getCollection("coll_23090170_A_2025");

            // CREATE: Insert data
            Document mahasiswa = new Document("name", "Yahya")
                    .append("prodi", "Informatika")
                    .append("email", "yahya001@gmail.com")
                    .append("age", 17);
            collection.insertOne(mahasiswa);
            System.out.println("Data Inserted");

            // READ: Menampilkan semua data
            System.out.println("\n=== All Mahasiswa ===");
            FindIterable<Document> allData = collection.find();
            for (Document d : allData) {
                System.out.println(d.toJson());
            }

            // UPDATE: Update email berdasarkan nama
            Bson filterUpdate = Filters.eq("name", "Yahya");
            Bson updateData = Updates.set("email", "yahya007@gmail.com");
            collection.updateOne(filterUpdate, updateData);
            System.out.println("\n=== Data Updated ===");

            // READ setelah update
            System.out.println("\n=== Data After Update ===");
            Document updatedDoc = collection.find(Filters.eq("name", "Yahya")).first();
            if (updatedDoc != null) {
                System.out.println(updatedDoc.toJson());
            } else {
                System.out.println("Data not found.");
            }

            // SEARCHING: Cari berdasarkan prodi
            System.out.println("\n=== Search by Prodi: Informatika ===");
            Bson searchFilter = Filters.eq("prodi", "Informatika");
            FindIterable<Document> searchResult = collection.find(searchFilter);
            for (Document d : searchResult) {
                System.out.println(d.toJson());
            }

            // DELETE: Hapus data berdasarkan nama
            Bson deleteFilter = Filters.eq("name", "yahya");
            collection.deleteOne(deleteFilter);
            System.out.println("\n=== Data Deleted ===");

            // READ setelah delete
            System.out.println("\n=== Data After Delete ===");
            FindIterable<Document> finalResult = collection.find();
            for (Document d : finalResult) {
                System.out.println(d.toJson());
            }

        } catch (Exception e) {
        }
    }   
}