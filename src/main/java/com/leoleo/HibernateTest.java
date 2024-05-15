package com.leoleo;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTest {

    private static final Scanner scanner = new Scanner(System.in);

    private static String getValidatedInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        if (input.matches("\\d+")) {
            System.out.println("Tidak valid. Tolong jangan isi angka saja.");
            return getValidatedInput(prompt);
        }
        if (!Pattern.matches("[\\p{Alnum}]+", input)) {
            System.out.println("Tidak valid. Jangan masukkan simbol");
            return getValidatedInput(prompt);
        }
        if (input.isEmpty()) {
            System.out.println("Tidak valid. Tolong isi inputan");
            return getValidatedInput(prompt);
        }
        
        return input;
    }

    private static int getValidatedInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        try {
            int value = Integer.parseInt(input);
            if (value < 0) {
                System.out.println("Input tidak boleh kurang dari 0.");
                return getValidatedInt(prompt);
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Input harus bilangan bulat");
            return getValidatedInt(prompt);
        }
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = null;

        try {
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("\nSilakan Pilih");
                System.out.println("1. Tambah Data (Pilih 1)");
                System.out.println("2. Ubah Data (Pilih 2)");
                System.out.println("3. Hapus Data (Pilih 3)");
                System.out.println("4. Lihat Data (Pilih 4)");
                System.out.println("5. Keluar (Pilih 5)");
                
                int pilihan = getValidatedInt("Pilihan: ");
                
                switch (pilihan) {
                    case 1:
                        transaction = session.beginTransaction();
                        Student newStudent = new Student();
                        newStudent.setName(getValidatedInput("Nama Mahasiswa: "));
                        newStudent.setAge(getValidatedInt("Umur Mahasiswa: "));
                        newStudent.setMajor(getValidatedInput("Jurusan Mahasiswa: "));
                        session.save(newStudent);
                        transaction.commit();
                        System.out.println("Penambahan Data Berhasil");
                        break;

                    case 2:
                        transaction = session.beginTransaction();
                        List<Student> students = session.createQuery("from Student", Student.class).list();
                        System.out.println("Daftar Mahasiswa");
                        for (Student student : students) {
                            System.out.println(student.toString());
                        }
                        session.getTransaction().commit();
                        
                        int updateId = getValidatedInt("ID Mahasiswa: ");
                        Student studentToUpdate = session.get(Student.class, updateId);
                        if (studentToUpdate != null) {
                            transaction = session.beginTransaction();
                            System.out.println("Mengubah data mahasiswa: " + studentToUpdate);
                            studentToUpdate.setName(getValidatedInput("Nama Mahasiswa: "));
                            studentToUpdate.setAge(getValidatedInt("Umur Mahasiswa: "));
                            studentToUpdate.setMajor(getValidatedInput("Jurusan Mahasiswa: "));
                            session.update(studentToUpdate);
                            transaction.commit();
                            System.out.println("Update Data Berhasil");
                        } else {
                            System.out.println("ID Mahasiswa tidak valid");
                        }
                        break;

                    case 3:
                        int deleteId = getValidatedInt("ID Mahasiswa: ");
                        Student studentToDelete = session.get(Student.class, deleteId);
                        if (studentToDelete != null) {
                            transaction = session.beginTransaction();
                            System.out.println("Menghapus data mahasiswa: " + studentToDelete);
                            session.delete(studentToDelete);
                            transaction.commit();
                            System.out.println("Penghapusan Data Berhasil");
                        } else {
                            System.out.println("ID Mahasiswa tidak valid");
                        }
                        break;

                    case 4:
                        transaction = session.beginTransaction();
                        List<Student> studentList = session.createQuery("from Student", Student.class).list();
                        System.out.println("Daftar Mahasiswa");
                        for (Student student : studentList) {
                            System.out.println(student.toString());
                        }
                        session.getTransaction().commit();
                        break;

                    case 5:
                        isRunning = false;
                        break;

                    default:
                        System.out.println("Inputan tidak valid, mohon memilih sesuai petunjuk");
                }
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
