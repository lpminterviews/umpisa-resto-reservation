package co.umpisa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @Id
    private String customerName;
    private String phoneNumber;
    private String email;
    private List<NotificationChannel> preferredChannels;

    public Customer() {
        // empty
    }

    public Customer(String customerName, String phoneNumber, String email, List<NotificationChannel> preferredChannels) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.preferredChannels = preferredChannels;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public List<NotificationChannel> getPreferredChannels() {
        return preferredChannels;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", preferredChannels=" + preferredChannels +
                '}';
    }
}
