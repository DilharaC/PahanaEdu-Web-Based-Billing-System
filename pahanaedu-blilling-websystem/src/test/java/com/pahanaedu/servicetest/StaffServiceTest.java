package com.pahanaedu.servicetest;

import com.pahanaedu.dao.StaffDAO;
import com.pahanaedu.model.Staff;
import com.pahanaedu.service.StaffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTest {

    private StaffDAO staffDAOMock;
    private StaffService staffService;

    @BeforeEach
    void setUp() {
        staffDAOMock = mock(StaffDAO.class);

        
        staffService = StaffService.getInstance();

        try {
            var daoField = StaffService.class.getDeclaredField("staffDAO");
            daoField.setAccessible(true);
            daoField.set(staffService, staffDAOMock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    
  @Test
  void testUpdatePassword() {
      when(staffDAOMock.updatePasswordWithHash(eq(1), anyString())).thenReturn(true);

      assertTrue(staffService.updatePassword(1, "newSecret"));
  }
    
    
  
  
    @Test
  void testGetStaffById() {
      Staff staff = new Staff();
      when(staffDAOMock.getStaffById(10)).thenReturn(staff);

      assertEquals(staff, staffService.getStaffById(10));
  }
    
    
    
    
    @Test
    void testCheckPasswordValid() {
        Staff staff = new Staff();
        staff.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("secret", org.mindrot.jbcrypt.BCrypt.gensalt()));
        when(staffDAOMock.getStaffById(1)).thenReturn(staff);

        assertTrue(staffService.checkPassword(1, "secret"));
    }

    
    
    @Test
    void testCreateStaff() throws SQLException {
        Staff staff = new Staff();
        staffService.createStaff(staff);

        verify(staffDAOMock, times(1)).addStaff(staff);
    }

    @Test
    void testLoginSuccess() {
        Staff expected = new Staff();
        when(staffDAOMock.login("user", "pass")).thenReturn(expected);

        Staff actual = staffService.login("user", "pass");

        assertEquals(expected, actual);
    }




    @Test
    void testGetAllStaff() {
        List<Staff> list = Arrays.asList(new Staff(), new Staff());
        when(staffDAOMock.getAllStaff()).thenReturn(list);

        assertEquals(2, staffService.getAllStaff().size());
    }

  

    @Test
    void testUpdateStaff() {
        Staff staff = new Staff();
        when(staffDAOMock.updateStaff(staff)).thenReturn(true);

        assertTrue(staffService.updateStaff(staff));
    }



}
