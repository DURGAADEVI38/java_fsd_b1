package com.main.dao_impl;

import com.main.dao.ProposalDAO;
import com.main.enums.PaymentStatus;
import com.main.enums.ProposalStatus;
import com.main.exceptions.ResourceNotFoundException;
import com.main.model.Proposal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProposalDAOImpl implements ProposalDAO {
    private final JdbcTemplate jdbcTemplate;
    public ProposalDAOImpl(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }

    private RowMapper<Proposal> mapper(){
        return (rs, num) -> {
            return new Proposal(
                    rs.getInt("id"),
                    rs.getInt("officer_id"),
                    PaymentStatus.valueOf(
                            rs.getString("paymentStatus")),
                    rs.getInt("policy_id"),
                    ProposalStatus.valueOf(
                            rs.getString("proposalStatus")),
                    rs.getInt("user_id"),
                    rs.getString("vehicle")

            );
        };
    }
    @Override
    public void update(Proposal proposal) {
        String sql = "update proposal SET paymentStatus = ? where id = ?";

        jdbcTemplate.update(sql,
                proposal.getPaymentStatus().toString(),
                proposal.getId()
        );
        System.out.println("Proposal updated successfully");
    }

    @Override
    public void insert(Proposal proposal) {
        String sql="insert into proposal(vehicle,proposalStatus,paymentStatus,user_id,policy_id,officer_id) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                proposal.getVehicle(),
                proposal.getProposalStatus().toString(),
                proposal.getPaymentStatus().toString(),
                proposal.getUser_id(),
                proposal.getPolicy_id(),
                proposal.getOfficer_id());
        System.out.println("Proposal Added successfully");

    }

    @Override
    public Proposal getByID(int id) throws  ResourceNotFoundException{
        String sql="select * from proposal where id=?";
        return jdbcTemplate.queryForObject(sql,mapper(), id);
    }

    @Override
    public List<Proposal> getAll() {
        String sql = "select * from proposal";

        return jdbcTemplate.query(sql,mapper());
    }

    @Override
    public void delete(int id) {
        String sql="delete from proposal where id=?";
        int row=jdbcTemplate.update(sql,id);
        if(row==0)
        {
            throw  new ResourceNotFoundException("Invalid Id");
        }
        System.out.println("Proposal deleted successfully");


    }
}
