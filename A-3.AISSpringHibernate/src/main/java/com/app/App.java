package com.app;

import com.app.config.AppConfig;
import com.app.dao.AuthDao;
import com.app.dao.PolicyDao;
import com.app.dao.ProposalDao;
import com.app.dao_impl.AuthDaoImpl;
import com.app.dao_impl.ProposalDaoImpl;
import com.app.enums.PaymentStatus;
import com.app.enums.ProposalStatus;
import com.app.exception.InvalidOwnershipException;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Policy;
import com.app.model.Proposal;
import com.app.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {
    public static  void main(String[] args)
    {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);

        Scanner sc=new Scanner(System.in);
        AuthDao authDao=context.getBean(AuthDaoImpl.class);
        ProposalDao proposalDao=context.getBean(ProposalDao.class);
        PolicyDao policyDao=context.getBean(PolicyDao.class);
        System.out.println("---AUTOMOBILE INSURANCE SYSTEM---");
        System.out.println("Enter the username");
        String username=sc.nextLine();
        System.out.println("Enter the password");
        String password=sc.nextLine();
        try{
                User user=authDao.login(username,password);
                switch (user.getRole().toString())
                {
                    case "CUSTOMER":
                        System.out.println("---Welcome "+username+"! ---");
                        while(true)
                        {
                            System.out.println("1. Add New Proposal");
                            System.out.println("2. Withdraw Proposal by id");
                            System.out.println("3. Fetch all Proposal");
                            System.out.println("0. Exit ");
                            int op = sc.nextInt();
                            if (op == 0)
                                break;
                            switch (op)
                            {
                                case 1:
                                    sc.nextLine();
                                    System.out.println("Enter vehicle name");
                                    String vehicle = sc.nextLine();
                                    System.out.println("Enter Policy Id");
                                    int policy_id=sc.nextInt();
                                    System.out.println("Enter Payment Status");
                                    String payment = sc.next();
                                    Policy policy = policyDao.getById(policy_id);

                                    proposalDao.save(new Proposal(vehicle,policy,PaymentStatus.valueOf(payment.toUpperCase())), username);
                                    System.out.println("Proposal added");
                                    break;

                                case 2:
                                    System.out.println("Enter Proposal Id to withdraw");
                                    int deleteId = sc.nextInt();

                                    try {
                                        Proposal proposal = proposalDao.getById(deleteId, username);
                                        proposalDao.delete(proposal);
                                        System.out.println("Proposal deleted successfully");
                                    }
                                    catch (ResourceNotFoundException | InvalidOwnershipException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 3:
                                    System.out.println("---ALL Proposal---");
                                    proposalDao.findAll(username).forEach(System.out::println);
                                    break;
                            }
                        }
                        break;
                    case "OFFICER":
                        System.out.println("---Welcome "+username+"!---");
                        while(true)
                        {
                            System.out.println("1. Reject Proposal by id");
                            System.out.println("2. Fetch all Proposal");
                            System.out.println("3. Update the status of Customer Proposal");
                            System.out.println("0. Exit ");
                            int op = sc.nextInt();
                            if (op == 0)
                                break;
                            switch (op) {
                                case 1:
                                    System.out.println(" Enter Id to Reject the Proposal");
                                    int deleteId = sc.nextInt();

                                    try {
                                        Proposal proposal = proposalDao.getById(deleteId, username);
                                        proposalDao.delete(proposal);
                                        System.out.println("Proposal deleted successfully");
                                    }
                                    catch (ResourceNotFoundException | InvalidOwnershipException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 2:
                                    System.out.println("---ALL Proposal---");
                                    proposalDao.findAll(username).forEach(System.out::println);
                                    break;
                                case 3:
                                    System.out.println("Enter proposal id to update");
                                    int id = sc.nextInt();
                                    // fetch ticket and check ownership
                                    try {
                                        Proposal proposal=proposalDao.getById(id,username);
                                        System.out.println("Enter the status of the Proposal");
                                        String status = sc.next();
                                        proposal.setProposalStatus(ProposalStatus.valueOf(status.toUpperCase()));
                                        proposalDao.update(proposal);
                                        System.out.println("Proposal updated");
                                    }
                                    catch(ResourceNotFoundException | InvalidOwnershipException e){
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                            }
                        }
                        break;
                }



        }
        catch (NoResultException e)
        {
            System.out.println("Invalid credentials.");

        }
        context.close();



    }
}
