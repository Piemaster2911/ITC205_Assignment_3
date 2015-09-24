package library.daos;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

public class MemberDAO implements IMemberDAO {
    
    List<IMember> members = new ArrayList<IMember>();
    IMemberHelper helper;
    
    public MemberDAO (IMemberHelper h) {
        if(h == null) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.print("Book helper is null!");
                e.printStackTrace();
            }
        }
        else {
            this.helper = h;
        }
    }

    @Override
    public IMember addMember(String firstName, String lastName,
            String contactPhone, String emailAddress) {
        IMember member = this.helper.makeMember(firstName, lastName, contactPhone, emailAddress, members.size() + 1);
        members.add(member);
        return member;
    }

    @Override
    public IMember getMemberByID(int id) {
        IMember member = null;
        for(int i = 0; i < members.size(); i++) {
            int a = members.get(i).getID();
            if(id == a) // if member exists with id
                member = members.get(i);
        }
        if(member != null) {
            return member;
        }
        else {
            return null;
        }
    }

    @Override
    public List<IMember> listMembers() {
        return members;
    }

    @Override
    public List<IMember> findMembersByLastName(String lastName) {
        List<IMember> memberList = new ArrayList<IMember>();
        for(int i = 0; i < members.size(); i++) {
            String l = members.get(i).getLastName();
            if(l.equals(lastName)) // if member found with matching last name
                memberList.add(members.get(i));
        }
        if(memberList.isEmpty()) {
            return null;
        }
        else {
            return memberList;
        }
    }

    @Override
    public List<IMember> findMembersByEmailAddress(String emailAddress) {
        List<IMember> memberList = new ArrayList<IMember>();
        for(int i = 0; i < members.size(); i++) {
            String l = members.get(i).getEmailAddress();
            if(l.equals(emailAddress)) // if member found with matching last name
                memberList.add(members.get(i));
        }
        if(memberList.isEmpty()) {
            return null;
        }
        else {
            return memberList;
        }
    }

    @Override
    public List<IMember> findMembersByNames(String firstName, String lastName) {
        List<IMember> memberList = new ArrayList<IMember>();
        for(int i = 0; i < members.size(); i++) {
            String l = members.get(i).getLastName();
            if(l.equals(lastName)) {  // if member found with matching last name
                String f = members.get(i).getFirstName(); // get first name
                if(f.equals(firstName)) // if first name matches
                    memberList.add(members.get(i));
            }
        }
        if(memberList.isEmpty()) {
            return null;
        }
        else {
            return memberList;
        }
    }

}
