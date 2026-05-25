package com.main;

import com.main.config.AppConfig;
import com.main.dao.ProposalDAO;
import com.main.dao_impl.ProposalDAOImpl;
import com.main.enums.PaymentStatus;
import com.main.enums.ProposalStatus;
import com.main.exceptions.ResourceNotFoundException;
import com.main.model.Proposal;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Scanner;

public class App {
public static  void  main(String[] args)
{
    AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
    Scanner scanner=new Scanner(System.in);
    ProposalDAO proposalDAO = context.getBean(ProposalDAOImpl.class);
    while(true)
    {
        System.out.println("1.Add Proposal");
        System.out.println("2.Delete Proposal");
        System.out.println("3.Update Proposal");
        System.out.println("4.Get All Proposal");
        System.out.println("5. Get Proposal By Id");
        System.out.println("0. Exit");
        int op= scanner.nextInt();
        if (op ==0)
            break;
        switch(op)
        {
            case 1:
                proposalDAO.insert(new Proposal(38,10, PaymentStatus.PENDING,3, ProposalStatus.INITIATED,3,"car"));
                break;
            case 2:
                System.out.println("Enter id to delete");
                int id= scanner.nextInt();
                try{
                    proposalDAO.delete(id);
                }
                catch (ResourceNotFoundException e) {
                    System.out.println("Invalid id");
                }
                context.close();
                break;
            case 3:
                System.out.println("Enter id to update");
                id=scanner.nextInt();
                try {
                    Proposal proposal = proposalDAO.getByID(id);
                    System.out.println("Enter Payment Status:");
                    scanner.nextLine();

                    String status = scanner.nextLine();

                    proposal.setPaymentStatus(
                            PaymentStatus.valueOf(status.toUpperCase())
                    );

                    proposalDAO.update(proposal);
                }
                catch (EmptyResultDataAccessException e) {
                    System.out.println("Invalid");
                }

                break;
            case 4:
                proposalDAO.getAll().forEach(System.out::println);
                break;
            case 5:
                System.out.println("Enter Proposal Id to fetch Proposal");
                id = scanner.nextInt();
                try {
                    Proposal proposal=proposalDAO.getByID(id);
                    System.out.println(proposal);
                }
                catch(EmptyResultDataAccessException e){
                    System.out.println("Invalid Id");
                }
                break;



        }
    }
}
}
