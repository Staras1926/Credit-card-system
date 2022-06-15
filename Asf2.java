package asf2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.RELATIVE;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.CANCEL_OPTION;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Asf2 {

    private static final String pub_file = ("public_key.dat");//arxeio xrhstwn
    private static final String priv_file = ("private_key.dat");//arxeio announcement
    private static final String all_users_file = ("all_users.dat");//arxeio announcement
    private static ArrayList<Wallet> WAL = new ArrayList<>();
    private static ArrayList<Wallet> WAL2 = new ArrayList<>();
    private static ArrayList<Wallet> WAL3 = new ArrayList<>();
    private static int counter = 0;
    private static boolean bool;

//edw grafei to private key
    private static void WritePrivateKeyToFile(String file, PrivateKey key, String type) throws IOException {
        FileOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;
        if ("priv".equals(type)) {
            System.out.println("Private Key was succesfully written to a file");
        } else {
            System.out.println("Public Key was succesfully written to a file");
        }
        try {

            fileOut = new FileOutputStream(file);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(key);
            objectOut.close();

        } catch (Exception e) {
        } finally {
            if (objectOut != null) {
                objectOut.close();
                if (fileOut != null) {
                    fileOut.close();
                }
            }
        }
    }
//edw grafei to public key

    private static void WritePublicKeyToFile(String file, PublicKey key, String type) throws IOException {//write se arxeio mono gia create announcement kai dhmiourgia arxeiou me xrhstes sth synarthsh check_logins
        FileOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;
        if ("priv".equals(type)) {
            System.out.println("Private Key was succesfully written to a file");
        } else {
            System.out.println("Public Key was succesfully written to a file");
        }
        try {

            fileOut = new FileOutputStream(file);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(key);
            objectOut.close();

        } catch (Exception e) {
        } finally {
            if (objectOut != null) {
                objectOut.close();
                if (fileOut != null) {
                    fileOut.close();
                }
            }
        }
    }
//grafw tous users sto file ths efarmoghs

    private static void WriteUserToFile(String file, User Obj) {
        File file2 = new File(file);
        if (file2.exists()) {
            try {
                System.out.println(Obj.toString());
                //FileOutputStream fileOut = new FileOutputStream(file2, true);
                FileOutputStream fileOut = new FileOutputStream(file, true);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut) {
                    @Override
                    public void writeStreamHeader() {
                    }
                };
                objectOut.writeObject(Obj);
                objectOut.flush();
                objectOut.reset();
                objectOut.close();
                System.out.println("\n\n Account with Username '" + Obj.getUsername() + "' was succesfully written to a file\n\n");

            } catch (Exception ex) {
            }
        } else {
            try {

                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(Obj);
                objectOut.flush();
                objectOut.reset();
                objectOut.close();
                System.out.println("\n\n Account with Username '" + Obj.getUsername() + "' was succesfully written to a file2\n\n");

            } catch (Exception ex) {
            }
        }
    }

    //diabazw ta public,private keys
    private static Object ReadKeys(String file) {//read file gia na dwsw sto client thn anakoinwsh gia mhnuma epibebaiwshs dhmiourgias

        try {

            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();

            objectIn.close();
            fileIn.close();
            return obj;

        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }
//diabazw tous users mono gia na epistrepsw int times an yparxoun oi users

    private static int ReadUserName(String file, String username) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file2 = new File(file);
        if (file2.exists()) {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            try {
                User obj;
                while ((obj = (User) objectIn.readObject()) != null) {
                    System.out.println("----- username sth read = " + obj.getUsername());

                    if (username.equals(obj.getUsername())) {
                        System.out.println("username = " + obj.getUsername());
                        System.out.println("ok");
                        objectIn.close();
                        fileIn.close();
                        return 1;//an 1 tote uparxei to name tou user
                    }
                }
                // objectIn.close();
            } catch (EOFException e) {
                objectIn.close();
                fileIn.close();
            }
            return 2;
        }
        System.out.println("cant find username");
        return 2;
    }
//diabazw tous users mono gia na epistrepsw to object pou thelw

    private static Object ReadUserName2(String file, String username) throws FileNotFoundException, IOException, ClassNotFoundException {
        //synarthsh gia na epistrepsei pisw ton user me auto to username

        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        User obj;
        System.out.println("username read = " + username);
        while ((obj = (User) objectIn.readObject()) != null) {
            if (username.equals(obj.getUsername())) {
                System.out.println("username sth read = " + obj.getUsername());
                System.out.println("ok");
                objectIn.close();
                fileIn.close();
                return obj;//an 1 tote uparxei to name tou userobjectIn.close();
            }
        }
        objectIn.close();
        fileIn.close();
        return null;

    }

    //dhmiourgia salt
    private static byte[] Salt() {
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[6];
        random.nextBytes(salt);
        return salt;
    }

    //dhmiourgia Hash apo password kai salt
    private static byte[] Hash(String password, byte[] salt, int iterations, int derivedKeyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength * 8);

        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return f.generateSecret(spec).getEncoded();
    }
//diabazw to Salt kai to epistrefw

    private static byte[] ReadSalt(String file, String username) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            User obj;
            while ((obj = (User) objectIn.readObject()) != null) {
                if (username.equals(obj.getUsername())) {
                    System.out.println("Salt read= " + obj.getSalt());
                    return obj.getSalt();
                }

            }
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException ex) {

        }
        return null;

    }
//bazw to summetriko key se neo fakelo pou ftiaxnw

    private static void symmetric_key_files(String name, String encrypted_symmetrickey) throws IOException {
        //briskw to fakelo tou project
        //ftiaxnw ena fakelo All Users gia tous xrhstes
        //ftiaxnw ena akoma fakelo gia kathe user
        //telos ftiaxnw to username.dat file gia kathe xrhsth me to symmetriko kleidi tou
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path + "\\All Users";
        System.out.println("Path 1 = " + path);
        Path realpath = Paths.get(path);
        if (Files.exists(realpath)) {//an yparxei o fakelos psaxnw mesa tou gia ton user
            File currDir2 = new File(realpath.toString());
            String path2 = currDir2.getAbsolutePath();
            path2 = path2 + "\\" + name;
            System.out.println("Path 1 = " + path2);
            Path realpath2 = Paths.get(path2);
            if (Files.exists(realpath2)) {//an yparxei o user elegxw an uparxei to .dat file me to username tou
                String fileName = name;
                fileName = fileName + ".dat";
                File file_dat = new File(path2, fileName);
                if (file_dat.exists()) {//an yparxei exw hdh to summetriko kleidi tou user grammeno
                    FileOutputStream fileOut = new FileOutputStream(file_dat, true);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                    //kalw th sunarthsh pou kanei 
                    objectOut.writeObject(encrypted_symmetrickey);
                    objectOut.flush();
                    objectOut.reset();
                    objectOut.close();
                } else {//an dn uparxei to ftiaxnw
                    //an symm doesnt exist ftiaxnw file
                    //File symmetrick_key_file = new File(fileName);
                    FileOutputStream fileOut = new FileOutputStream(file_dat);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                    //kalw th sunarthsh pou kanei 
                    objectOut.writeObject(encrypted_symmetrickey);
                    objectOut.flush();
                    objectOut.close();

                }

            } else {
                Files.createDirectory(realpath2);
                symmetric_key_files(name, encrypted_symmetrickey);
            }
        } else {//an yparxei o fakelos All Users ton ftiaxnw
            Files.createDirectory(realpath);
            symmetric_key_files(name, encrypted_symmetrickey);//kalw thn idia sunarthsh gia na ftiaksei ta ipoloipa files
        }

    }

    //sunarthsh pou kanei encrypt tme to public key kai epistrefei to text
    private static byte[] encryptpass(String hash) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] dataToEncrypt = hash.getBytes();
        byte[] encryptedData = null;
        //diabazw to public key
        PublicKey pubKey = (PublicKey) ReadKeys(Asf2.pub_file);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        encryptedData = cipher.doFinal(dataToEncrypt);
        return encryptedData;
    }

    private static Key symetric_key() throws NoSuchAlgorithmException {
        Key symmKey;
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256, rand);
        symmKey = generator.generateKey();
        System.out.println("sym key start = " + symmKey);
        System.out.println(Base64.getEncoder().encodeToString(symmKey.getEncoded()));
        return symmKey;

    }
//grafw th karta sto arxeio

    private static void WriteCardToFile(SealedObject Obj1, String username, Cipher cipher) throws FileNotFoundException, IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path + "\\All Users";
        System.out.println("Path 1 = " + path);
        Path realpath = Paths.get(path);
        if (Files.exists(realpath)) {
            File currDir2 = new File(realpath.toString());
            String path2 = currDir2.getAbsolutePath();
            path2 = path2 + "\\" + username;
            System.out.println("Path 1 = " + path2);
            Path realpath2 = Paths.get(path2);
            if (Files.exists(realpath2)) {
                String fileName = username;
                fileName = fileName + "cardfile" + ".dat";
                File file_dat = new File(path2, fileName);
                if (file_dat.exists()) {
                    System.out.println("writecardtofile if");
                    System.out.println("Path2 before encrypt = " + path2);
                    System.out.println("Path4 file dat before encrypt = " + file_dat);
                    FileOutputStream fileOut = new FileOutputStream(file_dat, true);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut) {
                        @Override
                        public void writeStreamHeader() {
                        }
                    };

                    objectOut.writeObject(Obj1);
                    objectOut.flush();
                    objectOut.reset();
                    objectOut.close();

                } else {
                    System.out.println("writecardtofile else");
                    FileOutputStream fileOut = new FileOutputStream(file_dat);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                    objectOut.writeObject(Obj1);
                    objectOut.flush();
                    objectOut.close();
                }
            } else {
                Files.createDirectory(realpath2);
                WriteCardToFile(Obj1, username, cipher);
            }
        } else {
            Files.createDirectory(realpath);
            WriteCardToFile(Obj1, username, cipher);

        }
    }

    private static void prosthiki_kartas(String username) throws IOException {

        JFrame frame4 = new JFrame("Add Card");
        JPanel panel4 = new JPanel();
        JLabel card_label = new JLabel("CARD TYPE:");
        JComboBox type = new JComboBox(new String[]{"VISA", "MASTERCARD", "PAYPAL"});
        JLabel number_label = new JLabel("CARD NUMBER:");
        JTextField number = new JTextField();
        JLabel date_label = new JLabel("EXPIRATION (MM / YY):");
        JTextField date = new JTextField(7);
        JLabel cvv_label = new JLabel("SECURITY CODE:");
        JTextField cvv = new JTextField(10);
        JLabel cardholder_label = new JLabel("CARDHOLDER NAME:");
        JTextField cardholder = new JTextField(20);
        JButton addCard = new JButton("Add Card");

        GridBagLayout layout = new GridBagLayout();
        panel4.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridwidth = 1;
        panel4.add(card_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridwidth = 1;
        panel4.add(type, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 10;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridwidth = 1;
        panel4.add(number_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 10;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridwidth = 13;
        panel4.add(number, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        panel4.add(date_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 21;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        panel4.add(date, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 40;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridwidth = 5;
        panel4.add(cardholder_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 41;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridwidth = 5;
        panel4.add(cardholder, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 10;
        gbc.gridy = 20;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridwidth = 5;
        panel4.add(cvv_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 10;
        gbc.gridy = 21;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridwidth = 1;
        panel4.add(cvv, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 5;
        gbc.gridy = 57;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.PAGE_END;
        panel4.add(addCard, gbc);

        addCard.addActionListener((ActionEvent ae) -> {
            try {
                //an yparxei h karta epistrefei 2
                int option = ReadUsersCards(number.getText(), username, "add");
                //an dn einai adeio kanena text kai to card number=16 psifia,to cvv=3 psifia kai date format idio
                if (((!(type.getSelectedItem().equals("")) && !(date.getText().equals("")) && !(cvv.getText().equals("")) && !(cardholder.getText().equals("")))) && (number.getText().length() == 16) && (cvv.getText().length() == 3) && (date.getText().length() == 5)) {
                    //an yparxei h karta
                    if (option == 2) {
                        System.out.println(" option==2");
                        JOptionPane.showMessageDialog(null, "card already exists!! typeanother one or exit");

                    } else if (option == 1) {
                        try {
                            System.out.println("option ==1");
                            String card_type_selected = (String) type.getSelectedItem();
                            Wallet wal = new Wallet(card_type_selected, number.getText(), date.getText(), cvv.getText(), cardholder.getText());

                            System.out.println("option==1 prosthikh kartas");
                            System.out.println(read_symm(username));
                            //diabazw to encrypted symmetriko kleidi tou xrhsth kai epistrefei to key opou fernw sth swsth morfh gia na kanw encrypt th karta
                            byte[] decodedEncryptedFirstString = Base64.getDecoder().decode(read_symm(username));
                            SecretKey secr_key = new SecretKeySpec(decodedEncryptedFirstString, 0, decodedEncryptedFirstString.length, "AES");

                            byte[] iv = new byte[16];
                            iv = ("symmmmmmmmmmmmmm".getBytes());
                            Cipher cipher = Cipher.getInstance("AES" + "/CBC/PKCS5Padding");
                            cipher.init(Cipher.ENCRYPT_MODE, secr_key, new IvParameterSpec(iv));

                            // dimourgia sealed object me thn karta mou
                            SealedObject sealed = new SealedObject(wal, cipher);
                            try {
                                //grafw thn karta sto file
                                System.out.println("before writecardtofile");
                                WriteCardToFile(sealed, username, cipher);

                            } catch (IOException ex) {
                                Logger.getLogger(Asf2.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println(wal.toString());
                            frame4.dispose();
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchPaddingException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidKeyException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidAlgorithmParameterException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalBlockSizeException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (type.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please check card type !!");
                } else if (number.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please check card number !!");
                } else if (date.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please check card date !!");
                } else if (cvv.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please check card cvv !!");
                } else if (cardholder.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please check card holder name !!");
                } else if (cvv.getText().length() != 3) {
                    JOptionPane.showMessageDialog(null, "Please check numbers of cvv  !!");
                } else if (date.getText().length() != 5) {
                    JOptionPane.showMessageDialog(null, "Please check card date, format = mm/yy  !!");
                } else if (number.getText().length() != 16) {
                    int diff = 16 - number.getText().length();
                    if (diff > 0) {
                        JOptionPane.showMessageDialog(null, "Please check card number,missing  " + diff + " numbers");

                    } else {
                        diff *= -1;
                        JOptionPane.showMessageDialog(null, "Please check card number,extra  " + diff + " numbers");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //otan pataw to koumpi anaireshs
        );

        frame4.add(panel4);
        frame4.setVisible(true);
        frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame4.setLocationRelativeTo(null);
        frame4.setIconImage(new ImageIcon("card.png").getImage().getScaledInstance(700, 700, 700));
        frame4.setResizable(false);
        frame4.setSize(400, 300);

    }
//epiloges add card,modify card....

    private static void ViewWallet(String username, String option) {
        if (option.equals("view")) {//an h viewWallet kalesthei gia probolh kartas
            JFrame f = new JFrame();
            JPanel p = new JPanel();
            JLabel card_label = new JLabel("CARD TYPE:");
            JComboBox type = new JComboBox(new String[]{"VISA", "MASTERCARD", "PAYPAL"});
            JButton j1 = new JButton("VIEW CARD");

            GridBagLayout layout = new GridBagLayout();
            p.setLayout(layout);
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            p.add(card_label, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.gridx = 0;
            gbc.gridy = 9;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            p.add(type, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.gridx = 0;
            gbc.gridy = 12;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            j1.setPreferredSize(new Dimension(130, 30));
            p.add(j1, gbc);

            j1.addActionListener((java.awt.event.ActionEvent ae) -> {
                System.out.println("inside viewwallet view");

                try {
                    String card_type_selected = (String) type.getSelectedItem();
                    //diabaze thn karta
                    ReadUsersCards(card_type_selected, username, "view");
                } catch (IOException ex) {
                    Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                }
                //viewCard(username + "cardfile.dat", card_number.getText());
                String card_type_selected = (String) type.getSelectedItem();
                //afou exei gemisei h WAL2 apo to kalesma ths ReadUsersCards apo panw an to eidos kartas einai idio me to eidos kartas ths listas kalw thn view
                for (Wallet w : WAL2) {
                    System.out.println("card : " + w.toString());
                    if (w.getEidos().equals(card_type_selected)) {

                        System.out.println("card : " + w.toString());
                        view(w.getEidos(), w.getCardNumber(), w.getDate(), w.getCVV(), w.getKatoxos());
                    }
                }
                f.dispose();
            });
            f.add(p);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setSize(400, 350);
            f.setLocationRelativeTo(null);

        } else if (option.equals("modify")) {//an h viewWallet kalesthei gia modify kartas kanw thn idia diadikasia alla otan diabazw th lista kalw thn modify()
            JFrame f = new JFrame();
            JPanel p = new JPanel();
            JLabel card_label = new JLabel("Card Number:");
            JTextField cardnum = new JTextField(20);
            JButton j1 = new JButton("VIEW CARD");

            GridBagLayout layout = new GridBagLayout();
            p.setLayout(layout);
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            p.add(card_label, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.gridx = 0;
            gbc.gridy = 9;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            p.add(cardnum, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.gridx = 0;
            gbc.gridy = 12;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            j1.setPreferredSize(new Dimension(130, 30));
            p.add(j1, gbc);
            j1.addActionListener((java.awt.event.ActionEvent ae) -> {
                System.out.println("inside viewwallet modify");
                try {
                    ReadUsersCards(cardnum.getText(), username, "delete");
                } catch (IOException ex) {
                    Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("inside viewwallter before for");
                for (Wallet w : WAL) {
                    System.out.println("card eksw apo if : " + w.toString());
                    if (w.getCardNumber().equals(cardnum.getText())) {
                        System.out.println("card mesa apo if : " + w.toString());
                        WAL2.add(w);
                    } else {
                        WAL3.add(w);
                    }
                }
                for (Wallet w : WAL2) {
                    try {
                        if (w.getCardNumber().equals(cardnum.getText())) {
                            System.out.println("w before modify = " + w.toString());
                            modify(w.getEidos(), w.getCardNumber(), w.getDate(), w.getCVV(), w.getKatoxos(), username);
                            break;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                f.dispose();
            });

            f.add(p);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setSize(400, 350);
            f.setLocationRelativeTo(null);
        }
    }
//oles oi epiloges gia add card,view card,modify card,delete card

    private static void epiloges(String username) {
        JFrame frame = new JFrame("Options");
        JPanel panel = new JPanel();
        JButton add_card_b = new JButton("Add card");
        JButton modify_card_b = new JButton("Modify Card");
        JButton display_card_b = new JButton("Show my Card");
        JButton delete_card_b = new JButton("Delete my Card");

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        add_card_b.setPreferredSize(new Dimension(130, 30));
        panel.add(add_card_b, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        modify_card_b.setPreferredSize(new Dimension(130, 30));
        panel.add(modify_card_b, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        display_card_b.setPreferredSize(new Dimension(130, 30));
        panel.add(display_card_b, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        delete_card_b.setPreferredSize(new Dimension(130, 30));
        panel.add(delete_card_b, gbc);
//add card
        add_card_b.addActionListener((java.awt.event.ActionEvent ae) -> {
            try {
                prosthiki_kartas(username);
            } catch (IOException ex) {
                Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//modify card
        modify_card_b.addActionListener((java.awt.event.ActionEvent ae) -> {
            ViewWallet(username, "modify");
        });
//view card
        display_card_b.addActionListener((java.awt.event.ActionEvent ae) -> {
            System.out.println("before viewallet");
            ViewWallet(username, "view");
        });
//delete card
        delete_card_b.addActionListener((java.awt.event.ActionEvent ae) -> {
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("DELETE START");
            delete(username);
        });
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
    }

//typwnei thn karta
    private static void view(String type, String number, String date, String cvv, String katoxos) {
        JFrame frame4 = new JFrame("Card");
        JPanel panel4 = new JPanel();
        JLabel card_label = new JLabel("CARD TYPE:");
        JLabel card_label2 = new JLabel(type);
        JLabel number_label = new JLabel("CARD NUMBER:");
        JLabel number_label2 = new JLabel(number);
        JLabel date_label = new JLabel("EXPIRATION (Month / Year):");
        JLabel date_label2 = new JLabel(date);
        JLabel cvv_label = new JLabel("SECURITY CODE:");
        JLabel cvv_label2 = new JLabel(cvv);
        JLabel cardholder_label = new JLabel("CARDHOLDER NAME:");
        JLabel cardholder_label2 = new JLabel(katoxos);

        GridBagLayout layout = new GridBagLayout();
        panel4.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(card_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(card_label2, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 100;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(number_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 100;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(number_label2, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(date_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        gbc.weightx = 2;
        panel4.add(date_label2, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 100;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(cvv_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 100;
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(cvv_label2, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(cardholder_label, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        panel4.add(cardholder_label2, gbc);

        frame4.setIconImage(new ImageIcon("card.png").getImage().getScaledInstance(700, 700, 700));
        frame4.add(panel4);
        frame4.setVisible(true);
        frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame4.setSize(400, 350);
        frame4.setLocationRelativeTo(null);
    }
//idia me view alla me dynatothta allaghs 

    private static void modify(String type, String number, String date, String cvv, String katoxos, String username) throws IOException {
        JFrame frame4 = new JFrame("Card");
        JPanel panel4 = new JPanel();
        JLabel card_label = new JLabel("CARD TYPE:");
        JComboBox type2 = new JComboBox(new String[]{type, "VISA", "MASTERCARD", "PAYPAL"});
        JLabel number_label = new JLabel("CARD NUMBER:");
        JTextField number2 = new JTextField(number);
        JLabel date_label = new JLabel("EXPIRATION (Month / Year):");
        JTextField date2 = new JTextField(date);
        JLabel cvv_label = new JLabel("SECURITY CODE:");
        JTextField cvv2 = new JTextField(cvv);
        JLabel cardholder_label = new JLabel("CARDHOLDER NAME:");
        JTextField cardholder = new JTextField(katoxos);
        String cancel = "dont_exit";
        boolean exist = false;

        do {
            GridBagLayout layout = new GridBagLayout();
            panel4.setLayout(layout);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(card_label, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(type2, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 100;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(number_label, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 100;
            gbc.gridy = 2;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(number2, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(date_label, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            gbc.weightx = 2;
            panel4.add(date2, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 100;
            gbc.gridy = 6;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(cvv_label, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 100;
            gbc.gridy = 7;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(cvv2, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 11;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(cardholder_label, gbc);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 13;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 5;
            panel4.add(cardholder, gbc);

            int result = JOptionPane.showConfirmDialog(frame4, panel4, "Modify Card", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int option = ReadUsersCards(number2.getText(), username, "add");
                if (!(type2.getSelectedItem().equals("")) && !(date2.getText().equals("")) && !(cvv2.getText().equals("")) && !(cardholder.getText().equals(""))) {
                    System.out.println("modify -option = " + option);
                    if (option == 2) {//an yparxei o user
                        JOptionPane.showMessageDialog(null, "card already exists!! typeanother one or exit");
                        exist = true;
                    } else if (option == 1) {
                        String card_type_selected = (String) type2.getSelectedItem();
                        Wallet wal = new Wallet(card_type_selected, number2.getText(), date2.getText(), cvv2.getText(), cardholder.getText());
                        WAL2.clear();
                        WAL2.add(wal);
                        System.out.println("WAL2 = " + WAL2.toString());
                        System.out.println("before writecardtofile");
                        for (Wallet w : WAL3) {
                            System.out.println("w of WAL3 inside modify before 2 ifs =" + w.toString());
                            if (w.getCardNumber().equals(number)) {
                                System.out.println("if 1 modify = " + w.getCardNumber());
                            } else {
                                System.out.println("if 2 modify = " + w.getCardNumber());
                                WAL2.add(w);
                            }

                            //an h lista periexei thn karta tou ebale o xrhsths gia diagrafh tote thn prospernaei
                            // if (w.getCardNumber().equals(number)) {
                            //   System.out.println("card : " + w.toString());
                            //view(w.getEidos(), w.getCardNumber(), w.getDate(), w.getCVV(), w.getKatoxos());
                            // } else {//alliws an dn uparxei kanei add(w) sth WAL3
                            // WAL3.add(w);
                            //}
                            // }
                        }
                        DeleteCard("modify", username);
                        cancel = "exit";
                        System.out.println(wal.toString());
                        frame4.dispose();
                    }
                } else if (type2.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please choose card type !!");
                } else if (number2.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill card number !!");
                } else if (date2.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill card date !!");
                } else if (cvv2.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill card cvv !!");
                } else if (cardholder.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill card holder !!");
                }
            } else if (result == CANCEL_OPTION) {
                frame4.dispose();
                cancel = "exit";
            }
        } while ((type2.getSelectedItem().equals("") || number2.getText().equals("") || date2.getText().equals("") || cvv2.getText().equals("") || cardholder.getText().equals("") || exist == true) && cancel.equals("dont_exit"));
        frame4.setIconImage(new ImageIcon("card.png").getImage().getScaledInstance(700, 700, 700));
        frame4.setSize(400, 350);
    }

    private static void delete(String username) {

        JFrame f = new JFrame();
        JPanel p = new JPanel();
        JLabel la1 = new JLabel("cardNumber");
        JTextField card_number = new JTextField(50);
        //JLabel card_label = new JLabel("CARD TYPE:");
        //JComboBox type = new JComboBox(new String[]{"VISA", "MASTERCARD", "PAYPAL"});
        GridBagLayout layout = new GridBagLayout();
        p.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        p.add(la1, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        p.add(card_number, gbc);
        int result = JOptionPane.showConfirmDialog(f, p, "DELETE", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        //String card_type_selected = (String) type.getSelectedItem();
        if (result == JOptionPane.OK_OPTION) {
            try {
                //diabazw thn karta p dinei o xrhsths kai thn kanw add se mia lista
                ReadUsersCards(card_number.getText(), username, "delete");

            } catch (IOException ex) {
                Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Wallet w : WAL) {
                System.out.println("card : " + w.toString());
                //an h lista periexei thn karta tou ebale o xrhsths gia diagrafh tote thn prospernaei 
                if (w.getCardNumber().equals(card_number.getText())) {
                    //   System.out.println("card : " + w.toString());
                    //view(w.getEidos(), w.getCardNumber(), w.getDate(), w.getCVV(), w.getKatoxos());
                } else {//alliws an dn uparxei kanei add(w) sth WAL3
                    WAL3.add(w);
                    //}
                }
            }
            DeleteCard(card_number.getText(), username);
        } else if (result == JOptionPane.CANCEL_OPTION) {
            f.dispose();
        }

    }
//briskei an yparxei o user me ta stoixeia kai epistrefei  2 alliws 1

    private static int ReadUsersCards(String cardNum, String username, String type) throws FileNotFoundException, IOException {
        System.out.println("inside ReadUsersCards");

        WAL.clear();
        WAL2.clear();
        WAL3.clear();

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path + "\\All Users";
        System.out.println("Path 1 = " + path);
        Path realpath = Paths.get(path);
        System.out.println("Realpath = " + realpath);
        if (Files.exists(realpath)) {
            File currDir2 = new File(realpath.toString());
            String path2 = currDir2.getAbsolutePath();
            path2 = path2 + "\\" + username;
            System.out.println("Path 1 = " + path2);
            Path realpath2 = Paths.get(path2);
            if (Files.exists(realpath2)) {
                try {
                    String fileName = username;
                    System.out.println("file for key = " + fileName + ".dat");
                    byte[] decodedEncryptedFirstString = Base64.getDecoder().decode(read_symm(username));
                    SecretKey secr_key = new SecretKeySpec(decodedEncryptedFirstString, 0, decodedEncryptedFirstString.length, "AES");
                    Cipher cipher = Cipher.getInstance("AES" + "/CBC/PKCS5Padding");
                    byte[] iv = new byte[16];
                    iv = ("symmmmmmmmmmmmmm".getBytes());
                    fileName = fileName + "cardfile" + ".dat";
                    File file_dat = new File(path2, fileName);
                    if (file_dat.exists()) {
                        System.out.println("file!!!!!!");

                        try {
                            System.out.println("file : " + file_dat);

                            if ("delete".equals(type)) {//an thn kalesei gia delete
                                cipher.init(Cipher.DECRYPT_MODE, secr_key, new IvParameterSpec(iv));
                                FileInputStream fileIn = new FileInputStream(file_dat);
                                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                                SealedObject obj;
                                System.out.println("ReadUsersCards delete");
                                //diabazw olo to file kai prosthetw ta stoixeia se mia lista WAL
                                while ((obj = (SealedObject) objectIn.readObject()) != null) {
                                    Wallet w = (Wallet) obj.getObject(cipher);
                                    System.out.println("while-delete inside ReadUsersCards object = " + w.toString());
                                    WAL.add(w);
                                }
                                objectIn.close();
                                fileIn.close();
                                return 1;
                            } else if ("view".equals(type)) {//an thn kalesei gia view 
                                cipher.init(Cipher.DECRYPT_MODE, secr_key, new IvParameterSpec(iv));
                                FileInputStream fileIn = new FileInputStream(file_dat);
                                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                                SealedObject obj;
                                System.out.println("ReadUsersCards view");
                                //diabazw olo to file kai prosthetw ta stoixeia se mia lista WAL2
                                while ((obj = (SealedObject) objectIn.readObject()) != null) {
                                    Wallet w = (Wallet) obj.getObject(cipher);
                                    System.out.println("while-view inside ReadUsersCards object = " + w.toString());
                                    WAL2.add(w);
                                }
                                objectIn.close();
                                fileIn.close();
                                return 1;
                            } else if ("add".equals(type)) {//an thn kalesei gia add card
                                cipher.init(Cipher.DECRYPT_MODE, secr_key, new IvParameterSpec(iv));
                                FileInputStream fileIn = new FileInputStream(file_dat);
                                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                                SealedObject obj;
                                try {
                                    while ((obj = (SealedObject) objectIn.readObject()) != null) {
                                        Wallet w = (Wallet) obj.getObject(cipher);
                                        WAL3.add(w);
                                    }
                                } catch (EOFException e) {
                                    objectIn.close();
                                    fileIn.close();
                                }
                                try {
                                    FileInputStream fileIn2 = new FileInputStream(file_dat);
                                    ObjectInputStream objectIn2 = new ObjectInputStream(fileIn2);
                                    //diabazw to file mexri n brw thn idia karta.an thn brw epistrefw 2 gia na enhmerwthei o xrhsths
                                    while ((obj = (SealedObject) objectIn2.readObject()) != null) {
                                        Wallet w = (Wallet) obj.getObject(cipher);
                                        if (cardNum.equals(w.getCardNumber())) {
                                            System.out.println("card number = " + w.getCardNumber() + "  and exists ");
                                            System.out.println("ok");
                                            objectIn2.close();
                                            return 2;//an 1 tote uparxei to name tou user
                                        }
                                    }
                                    objectIn2.close();
                                    fileIn2.close();
                                } catch (EOFException e) {
                                    objectIn.close();
                                    fileIn.close();
                                }
                                return 1;
                            }

                        } catch (IOException | ClassNotFoundException ex) {
                            System.out.println("File ended");
                        } catch (InvalidKeyException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidAlgorithmParameterException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalBlockSizeException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BadPaddingException ex) {
                            Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {

                        FileOutputStream fileOut = new FileOutputStream(file_dat);
                        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                        objectOut.close();
                        fileOut.close();
                        ReadUsersCards(cardNum, username, type);
                        return 1;
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(Asf2.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("no file user");
                Files.createDirectory(realpath2);
                ReadUsersCards(cardNum, username, type);
            }
        } else {
            System.out.println("no main file");
            Files.createDirectory(realpath);
            ReadUsersCards(cardNum, username, type);
        }
        return 0;
    }

    private static void eggrafh() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, FileNotFoundException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        JFrame frame1 = new JFrame("Sign Up");
        JPanel panel1 = new JPanel();
        JLabel name_label = new JLabel("Name");
        JTextField name = new JTextField(20);
        JLabel surname_label = new JLabel("Surname");
        JTextField surname = new JTextField(20);
        JLabel email_label = new JLabel("E-mail");
        JTextField email = new JTextField(40);
        JLabel username_label = new JLabel("Username");
        JTextField username = new JTextField(20);
        JLabel password_label = new JLabel("Master Password");
        JPasswordField pass = new JPasswordField(35);
        JLabel verify_label = new JLabel("Verify Password");
        JPasswordField verify_pass = new JPasswordField(35);

        int exist = 0;
        String cancel = "dont_exit";//metablhth gia na bgei apo th while(twn getText()) an pathsw to cancel koumpi sta grafika sign up
        String exit_exist = "false";//metablhth gia na bgei apo th while(tou exist) an pathsw to cancel koumpi sta grafika sign up
        do {
            do {
                GridBagLayout layout = new GridBagLayout();
                panel1.setLayout(layout);
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(name_label, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(name, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 6;
                gbc.gridwidth = 5;
                gbc.insets = new Insets(10, 10, 10, 10);
                panel1.add(surname_label, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 9;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(surname, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 12;
                gbc.gridwidth = 500;
                gbc.insets = new Insets(10, 10, 10, 10);
                panel1.add(email_label, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 15;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(email, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 17;
                gbc.gridwidth = 500;
                gbc.insets = new Insets(10, 10, 10, 10);
                panel1.add(username_label, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 19;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(username, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 21;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(password_label, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 23;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(pass, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 26;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(verify_label, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 28;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel1.add(verify_pass, gbc);

                int result = JOptionPane.showConfirmDialog(frame1, panel1, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    //an exw kena stoixeia kai to password me to verify password dn einai idia
                    if (!(name.getText().equals("")) && !(surname.getText().equals("")) && !(email.getText().equals("")) && !(username.getText().equals("")) && !(pass.getText().equals("")) && (pass.getText().equals(verify_pass.getText()))) {
                        System.out.println(username.getText());
                        exist = ReadUserName(all_users_file, username.getText());
                        System.out.println("exist = " + exist);
                        if (exist == 1) {//an epistrepsei 1 h ReadUserName shmenei oti to username uparxei kai prepei na eisagei allo username
                            JOptionPane.showMessageDialog(null, "Username already exists! Please use another Username !");
                        } else {
                            Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
                            //dimiourgia salt
                            byte[] bSalt = Salt();
                            System.out.println("Bsalt = " + bSalt);
                            //dhmiourgia Hash
                            String strHash = encoder.encodeToString(Hash(pass.getText(), bSalt, 1, 8)); // Byte to String
                            System.out.println("strHash  ");
                            System.out.println(strHash);
                            //ecrypt to hash me public key
                            String encryptedpassword = Base64.getEncoder().encodeToString(encryptpass(strHash));
                            User Us = new User(name.getText(), surname.getText(), email.getText(), username.getText(), encryptedpassword, bSalt);
                            //grafw ton user sto file
                            WriteUserToFile(all_users_file, Us);
                            //dhmiorgia symmetrikou kleidiou
                            Key key = symetric_key();
                            String symmetrickey = Base64.getEncoder().encodeToString(key.getEncoded());
                            System.out.println("SYMMETRIC KEY = " + symmetrickey);

                            //encrypt symmetric key me to public key
                            String encrypted_symmetrickey = Base64.getEncoder().encodeToString(encryptpass(symmetrickey));
                            System.out.println("key after encrypt ");
                            System.out.println(encrypted_symmetrickey);

                            //dhmiourgia fakelou gia olous tous xrhstes
                            symmetric_key_files(username.getText(), encrypted_symmetrickey);
//komple mexri edw
                            //create folder for all users symetric keys 
                            // symmetric_key_files(username.getText(), encrypted_symmetrickey);
                            exit_exist = "true";
                        }

                    }//an kapio apo ta text einai adeio tote tou leei ti prepei n sumplhrwsei
                    else if ((name.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Please fill Name !!");
                    } else if ((surname.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Please fill Surname !!");
                    } else if ((email.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Please fill email !!");
                    } else if ((username.getText().equals(""))) {
                        JOptionPane.showMessageDialog(null, "Please fill Username !!");
                    } else if (pass.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please fill Password !!");
                    } else if (verify_pass.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please fill Verify Password !!");
                    } else if (!(pass.getText().equals(verify_pass.getText()))) {
                        JOptionPane.showMessageDialog(null, "Passwords dont match!! Retype");
                    }

                    // Wallet w = new Wallet("", "", "", "", "");
                    //WriteCardToFile(w, username.getText());
                } else if (result == CANCEL_OPTION) {
                    frame1.dispose();
                    cancel = "exit";
                    exit_exist = "true";
                }

            } while (((name.getText().equals("")) || (surname.getText().equals("")) || (email.getText().equals("")) || (username.getText().equals("")) || (pass.getText().equals("")) || !(pass.getText().equals(verify_pass.getText()))) && (cancel.equals("dont_exit")));

        } while (exist == 1 && exit_exist.equals("false"));

    }

    private static void logIn() throws IOException, FileNotFoundException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        if (counter < 3) {//an ta log in attempts einai mikrotera apo 3 kane log in
            String cancel = "dont_exit";
            JFrame frame2 = new JFrame("Log In");
            JPanel panel2 = new JPanel();
            JLabel lab2 = new JLabel("Username");
            JTextField username = new JTextField(20);
            JLabel lab3 = new JLabel("Password");
            JPasswordField pass = new JPasswordField(20);
            do {
                GridBagLayout layout = new GridBagLayout();
                panel2.setLayout(layout);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel2.add(lab2, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel2.add(username, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 6;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel2.add(lab3, gbc);

                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.gridx = 0;
                gbc.gridy = 9;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridwidth = 5;
                panel2.add(pass, gbc);
                int result = JOptionPane.showConfirmDialog(frame2, panel2, "Log in", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    if (!(username.getText().equals("")) && !(pass.getText().equals(""))) {

                    } else if ((username.getText().equals(""))) {//an kapio apo ta text einai adeio tote tou leei ti prepei n sumplhrwsei
                        JOptionPane.showMessageDialog(null, "Please fill username !!");
                    } else if (pass.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please fill password !!");
                    }
                    int exist;
                    //diabazw to file an uparxei o user epistrefei 1 alliws 2
                    exist = ReadUserName(all_users_file, username.getText());
                    System.out.println("exist login = " + exist);
                    // exist1 = ReadUserName(all_users_file, pass.getText());
                    if (exist != 1) {
                        counter = count_logins(counter);
                        JOptionPane.showMessageDialog(frame2, "Wrong credentials");
                        if (counter == 3) {
                            JOptionPane.showMessageDialog(frame2, "Too many wrong attempts for log in!!!");
                            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        }
                    } else {
                        //an yparxei o xrhsths diabazw to salt apo to arxeio tou
                        byte[] salt = ReadSalt(all_users_file, username.getText());
                        System.out.println("salt read = " + salt);
                        //System.out.println("bytes = " + salt.getBytes());
                        System.out.println(pass.getText());
                        System.out.println(pass.getPassword());
                        System.out.println("Salt getbytes = " + salt);
                        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
                        //kanw to hash me to salt kai password pou edwse
                        String strHash = encoder.encodeToString(Hash(pass.getText(), salt, 1, 8));
                        System.out.println("hash from password and ReadSalt = " + strHash);

                        //diabazw to file gia na brw to object me auto to username 
                        User user = (User) ReadUserName2(all_users_file, username.getText());
                        //pernw to private key kai kanw decrypt  to hash tou user apo to file
                        PrivateKey privateKey = (PrivateKey) ReadKeys(priv_file);
                        byte[] decodedEncryptedFirstString = Base64.getDecoder().decode(user.getPassword());

                        Cipher publicDecryptCipher = Cipher.getInstance("RSA");
                        publicDecryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
                        byte[] decryptedFirstStringByte = publicDecryptCipher
                                .doFinal(decodedEncryptedFirstString);
                        String decodedhash = new String(decryptedFirstStringByte);
                        System.out.println("Decrypted Hash from User: " + Base64.getEncoder().encodeToString(decryptedFirstStringByte));
                        //an to hash einai idio me to hash pou egine decrypt apo to file
                        if (decodedhash.equals(strHash)) {
                            epiloges(username.getText());
                        }

                    }

                } else if (result == CANCEL_OPTION) {
                    frame2.dispose();
                    cancel = "exit";
                }
            } while (((username.getText().equals("")) || (pass.getText().equals(""))) && (cancel.equals("dont_exit")) && counter < 3);
            frame2.setIconImage(new ImageIcon("card.png").getImage().getScaledInstance(700, 700, 700));
            frame2.setSize(400, 350);
        } else {//alliws mhn afhseis na kanei log in kaneis
            JOptionPane.showMessageDialog(null, "Too many wrong attempts for log in!!!Try again later");
        }
    }
//metraei ta log in attempts

    private static int count_logins(int counter) {

        counter++;
        return counter;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IOException, FileNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //diabazw ta arxeia me ta kleidia an uparxoun dn mpenei sth while
        PublicKey pub_test = (PublicKey) Asf2.ReadKeys(pub_file);
        PrivateKey priv_test = (PrivateKey) Asf2.ReadKeys(priv_file);
        //an dn uparxoun ektelei th while mia fora kai sto telos ths while diabazei an upaxroun ta arxeia gia na mhn ektelestei ksana
        while ((pub_test == null) && (priv_test == null) && (bool = true)) {

            //generator
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            //keys
            PublicKey pub = kp.getPublic();
            PrivateKey priv = kp.getPrivate();
            KeyFactory keyfac = KeyFactory.getInstance("RSA");
            System.out.println("public key");
            System.out.println(Base64.getEncoder().encodeToString(pub.getEncoded()));
            System.out.println("private key");
            System.out.println(Base64.getEncoder().encodeToString(priv.getEncoded()));
            //grafw ta kleidia
            Asf2.WritePublicKeyToFile(pub_file, pub, "pub");
            Asf2.WritePrivateKeyToFile(priv_file, priv, "priv");

            //gia na kanei mai fora thn while diabazw pali to arxeio.an ftiaxthke dn tha ksanatreksei 2rh fora
            pub_test = (PublicKey) Asf2.ReadKeys(pub_file);
            priv_test = (PrivateKey) Asf2.ReadKeys(priv_file);

            bool = false;
        }
//ftiaxnw to file twn users etsi wste na elleksw sth eggrafh to exist
        // File file = new File(all_users_file);

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton signup = new JButton("Sign up");
        JButton login = new JButton("Log in");
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 5;
        signup.setPreferredSize(new Dimension(100, 30));
        panel.add(signup, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 7;
        login.setPreferredSize(new Dimension(100, 30));
        panel.add(login, gbc);
        User user = new User("", "", "", "", "", null);
        WriteUserToFile(all_users_file, user);

        //an pathsei sign up
        signup.addActionListener((java.awt.event.ActionEvent ae) -> {
            try {
                eggrafh();//egrafh tou user

            } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException | ClassNotFoundException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(Asf2.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        //an pathsei log in
        login.addActionListener((java.awt.event.ActionEvent ae) -> {
            try {
                logIn();

            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(Asf2.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
    }

    private static void DeleteCard(String option, String username) {

        System.out.println("inside deletecard");
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path + "\\All Users";
        System.out.println("Path 1 = " + path);
        Path realpath = Paths.get(path);
        System.out.println("Realpath = " + realpath);
        if (Files.exists(realpath)) {
            File currDir2 = new File(realpath.toString());
            String path2 = currDir2.getAbsolutePath();
            path2 = path2 + "\\" + username;
            System.out.println("Path 1 = " + path2);
            Path realpath2 = Paths.get(path2);
            if (Files.exists(realpath2)) {
                String fileName = username;
                fileName = fileName + "cardfile" + ".dat";
                File file_dat = new File(path2, fileName);
                if (file_dat.exists()) {
                    System.out.println("exist? " + file_dat.exists());
                    try {
                        System.out.println("file!!!!!!");
                        System.out.println("file : " + file_dat);
                        FileOutputStream fileOut = new FileOutputStream(file_dat);
                        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                        //to option einai mono gia th modify mono ekei ekteleite auth h if.se oles tis alles periptwseis douleuei h else
                        if (option.equals("modify")) {

                            for (Wallet w : WAL2) {
                                System.out.println("w inside deletecard =" + w.toString());
                                //kanoume decrypt to symm key kai prin kanoume writeobject to encrypted Sealedobject
                                byte[] decodedEncryptedFirstString = Base64.getDecoder().decode(read_symm(username));
                                SecretKey secr_key = new SecretKeySpec(decodedEncryptedFirstString, 0, decodedEncryptedFirstString.length, "AES");

                                byte[] iv = new byte[16];
                                iv = ("symmmmmmmmmmmmmm".getBytes());
                                Cipher cipher = Cipher.getInstance("AES" + "/CBC/PKCS5Padding");

                                cipher.init(Cipher.ENCRYPT_MODE, secr_key, new IvParameterSpec(iv));

                                // dimourgia sealed object me thn karta mou
                                SealedObject sealed = new SealedObject(w, cipher);

                                objectOut.writeObject(sealed);
                                objectOut.flush();
                            }

                            objectOut.close();
                            fileOut.close();
                        } else {
                            //gia th lista me ola ta object ektos autou pou theloume n diabasoume
                            for (Wallet w : WAL3) {
                                //kanoume decrypt to symm key kai prin kanoume writeobject to encrypted Sealedobject
                                byte[] decodedEncryptedFirstString = Base64.getDecoder().decode(read_symm(username));
                                SecretKey secr_key = new SecretKeySpec(decodedEncryptedFirstString, 0, decodedEncryptedFirstString.length, "AES");

                                byte[] iv = new byte[16];
                                iv = ("symmmmmmmmmmmmmm".getBytes());
                                Cipher cipher = Cipher.getInstance("AES" + "/CBC/PKCS5Padding");

                                cipher.init(Cipher.ENCRYPT_MODE, secr_key, new IvParameterSpec(iv));

                                // dimourgia sealed object me thn karta mou
                                SealedObject sealed = new SealedObject(w, cipher);

                                objectOut.writeObject(sealed);
                                objectOut.flush();
                            }
                            objectOut.close();
                            fileOut.close();

                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Asf2.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
                        Logger.getLogger(Asf2.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                System.out.println("no file");
            }
        }
    }
//diabazw to symmetriko kleidi kai to kanw decrypt me to private key

    private static String read_symm(String username) {
        //akolouthw diadikasia gia n ftasw stou fakelo me to symm key
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path + "\\All Users";
        System.out.println("Path 1 = " + path);
        Path realpath = Paths.get(path);
        if (Files.exists(realpath)) {
            File currDir2 = new File(realpath.toString());
            String path2 = currDir2.getAbsolutePath();
            path2 = path2 + "\\" + username;
            System.out.println("Path 1 = " + path2);
            Path realpath2 = Paths.get(path2);
            if (Files.exists(realpath2)) {
                String fileName = username;
                fileName = fileName + ".dat";
                File file_dat = new File(path2, fileName);
                if (file_dat.exists()) {
                    try {
                        String file = file_dat.getAbsolutePath();
                        System.out.println("file= " + file);
                        String symmKey = (String) ReadKeys(file);
                        System.out.println("SYMMETRIC KEY = " + symmKey);
                        //diabazw private key kai kanw decrypt to symm key me to private key
                        PrivateKey privateKey = (PrivateKey) ReadKeys(priv_file);
                        byte[] decodedEncryptedFirstString = Base64.getDecoder().decode(symmKey);

                        Cipher publicDecryptCipher = Cipher.getInstance("RSA");
                        publicDecryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
                        byte[] decryptedFirstStringByte = publicDecryptCipher
                                .doFinal(decodedEncryptedFirstString);
                        String decodedhash = new String(decryptedFirstStringByte);
                        System.out.println("Decrypted symmetrick key : " + decodedhash);

                        //epistrefw to string tou key
                        return decodedhash;

                    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                        Logger.getLogger(Asf2.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return null;
    }

}
