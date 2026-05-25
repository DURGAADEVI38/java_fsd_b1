package com.controller;

import com.config.HibernateConfig;
import com.enums.PaymentStatus;
import com.enums.ProposalStatus;
import com.exception.ResourceNotFoundException;
import com.model.InsuranceOfficer;
import com.model.Policy;
import com.model.Proposal;
import com.model.User;
import com.service.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        HibernateConfig.getSessionFactory();
        Session session = HibernateConfig.getSessionFactory().openSession();
        Scanner sc = new Scanner(System.in);
        ProposalService proposalService = new ProposalService(session);
        policyService policyService = new policyService(session);
        UserService userService = new UserService(session);
        InsuranceService insuranceService = new InsuranceService(session);
        AuthService authService = new AuthService(session);
        System.out.println("---------- AUTOMOBILE INSURANCE LOGIN ---------");
        System.out.println("Enter Username:");
        String username = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();
        User user = null;

        try {
            user = authService.login(username, password);
        } catch (Exception e) {
            user = null;
        }
        if (user != null) {
            System.out.println("LOGIN SUCCESS AS CUSTOMER");
            while (true) {
                System.out.println("---- CUSTOMER MENU ----");
                System.out.println("1. Add Proposal");
                System.out.println("2. Fetch All Proposals");
                System.out.println("0. Exit");
                int op = sc.nextInt();
                if (op == 0) break;
                switch (op) {
                    case 1:
                        Proposal proposal = new Proposal();
                        proposal.setVehicle("Bike");
                        proposal.setProposalStatus(ProposalStatus.INITIATED);
                        proposal.setPaymentStatus(PaymentStatus.PAID);
                        Policy policy = policyService.getById(2);
                        User users = userService.getById(user.getId());
                        InsuranceOfficer officer = insuranceService.getById(1);
                        proposal.setPolicy(policy);
                        proposal.setUser(users);
                        proposal.setOfficer(officer);
                        proposalService.insert(proposal);
                        System.out.println("Proposal Added");
                        break;
                    case 2:
                        List<Proposal> list = proposalService.getAllProposals();
                        list.forEach(System.out::println);
                        break;
                    default:
                        System.out.println("Invalid Option");
                }
            }
        } else {
            InsuranceOfficer officer = null;
            try {
                officer = authService.loginOfficer(username, password);
            } catch (Exception e) {
                officer = null;
            }
            if (officer != null) {
                System.out.println("LOGIN SUCCESS AS INSURANCE OFFICER");
                while (true) {
                    System.out.println("---- INSURANCE OFFICER MENU ----");
                    System.out.println("1. Fetch All Proposals");
                    System.out.println("2. Delete Proposal");
                    System.out.println("3. Update Proposal");
                    System.out.println("0. Exit");
                    int op = sc.nextInt();
                    if (op == 0) break;
                    switch (op) {
                        case 1:
                            List<Proposal> list = proposalService.getAllProposals();
                            list.forEach(System.out::println);
                            break;
                        case 2:
                            System.out.println("Enter Proposal Id:");
                            int id = sc.nextInt();
                            try {
                                proposalService.deleteRecord(id);
                                System.out.println("Deleted");
                            } catch (ResourceNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 3:
                            System.out.println("Enter Proposal Id:");
                            id = sc.nextInt();
                            try {
                                Proposal proposal = proposalService.getById(id);
                                sc.nextLine();
                                System.out.println("Enter Vehicle:");
                                proposal.setVehicle(sc.nextLine());
                                System.out.println("Enter Proposal Status:");
                                proposal.setProposalStatus(ProposalStatus.valueOf(sc.next().toUpperCase()));
                                System.out.println("Enter Payment Status:");
                                proposal.setPaymentStatus(PaymentStatus.valueOf(sc.next().toUpperCase()));
                                proposalService.insert(proposal);
                                System.out.println("Updated");
                            } catch (ResourceNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        default:
                            System.out.println("Invalid Option");
                    }
                }
            } else {
                System.out.println("Invalid username/password");
            }
        }

        sc.close();
        session.close();
    }
}