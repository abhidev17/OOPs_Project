package view;

import javax.swing.*;
import java.awt.*;

public class NavigationBarPanel extends JPanel {
    private final JButton backBtn;
    private final JButton homeBtn;
    private final JButton logoutBtn;
   private String currentRole = ""; // track user role (rider, driver, admin)
    private boolean loggedIn = false;

    public void setLoggedIn(boolean status) {
        this.loggedIn = status;
        
    }


   

    public NavigationBarPanel(RideShareMobileUI ui) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
        setBackground(new Color(52, 152, 219)); // same blue as signup header
        
        //currentRole = ui.getCurrentUserRole();
        //loggedIn = ui.isLoggedIn();

        // ===== Buttons =====
        backBtn = new JButton("Back");
        homeBtn = new JButton("Home");
        logoutBtn = new JButton("Logout");

        // ===== Common Styling =====
        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);
        Color btnColor = Color.WHITE;

        for (JButton btn : new JButton[]{backBtn, homeBtn, logoutBtn}) {
            btn.setFont(btnFont);
            btn.setForeground(btnColor);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(41, 128, 185));
            btn.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // ===== Back Button =====
        backBtn.addActionListener(e -> ui.goBack());

        // ===== Home Button =====
        homeBtn.addActionListener(e -> {
            if (!ui.isLoggedIn()) {
                JOptionPane.showMessageDialog(
                    this,
                    "<html><div style='text-align:center; margin-top:-15px;'>Please login or sign up first!</div></html>",
                    "Access Denied",
                    JOptionPane.WARNING_MESSAGE
                );
                ui.showScreen("login");
                return;
            }

            // Get the current role dynamically
            String role = ui.getCurrentUserRole() != null ? ui.getCurrentUserRole() : "";

            switch (role) {
                case "rider" -> ui.showScreen("userhome");
                case "driver" -> ui.showScreen("providerhome");
                case "admin" -> ui.showScreen("admin");
                default -> ui.showScreen("login");
            }
        });


        // ===== Logout Button =====
        logoutBtn.addActionListener(e -> {
            if (loggedIn) {
                loggedIn = false;
                ui.setLoggedIn(false);
                //JOptionPane.showMessageDialog(this, "You have logged out successfully.");
                ui.showScreen("login");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "You are not logged in!", 
                    "Logout Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(backBtn);
        add(homeBtn);
        add(logoutBtn);
    }

    // ===== Dynamic visibility control =====
    public void update(String currentScreen) {
        boolean isLogin = currentScreen.equals("login");
        boolean isRiderSignup = currentScreen.contains("rider_signup");
        boolean isDriverSignup = currentScreen.contains("driver_signup");
        boolean isSignup = currentScreen.contains("signup_choice");
        boolean isUserHome = currentScreen.equals("userhome");
        boolean isDriverHome = currentScreen.equals("providerhome");
        boolean isAdminHome = currentScreen.equals("admin");

        // Hide buttons on login/signup pages
        if (isLogin) {
            backBtn.setVisible(false);
            homeBtn.setVisible(false);
            logoutBtn.setVisible(false);
            return;
        }
        if (isRiderSignup) {
            backBtn.setVisible(true);
            homeBtn.setVisible(false);
            logoutBtn.setVisible(false);
            return;
        }
        if (isSignup) {
            backBtn.setVisible(true);
            homeBtn.setVisible(false);
            logoutBtn.setVisible(false);
            return;
        }
        if (isDriverSignup) {
            backBtn.setVisible(true);
            homeBtn.setVisible(false);
            logoutBtn.setVisible(false);
            return;
        }
        // Hide Back and Home on respective home screens
        if (isUserHome || isDriverHome || isAdminHome) {
            backBtn.setVisible(false);
            homeBtn.setVisible(false);
            logoutBtn.setVisible(true);
        } else {
            backBtn.setVisible(true);
            homeBtn.setVisible(true);
            logoutBtn.setVisible(true);
        }
    }
}