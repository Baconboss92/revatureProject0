package com.helloapplii;

import java.util.ArrayList;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

// I am getting a concern message when I run this about incompatable versions
// I have no idea how to fix it and I am out of time to do so
// Tests still run fine...
// Also most of these test are really not worth it and are just random BS that use very similar tests
// My App is too simple and I am too new to unit testing to come up with anyting good
public class AppTest {
    @Mock
    private calRep mockRepository;
    private calSer service;
    private AutoCloseable closeable;
    
    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new calSer(mockRepository);
    }
    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    // God this test name format looks stupid, I understand why they reccommend
    // why I shold do this online but honestly I think I should of stuck to comments
    @Test
    public void testIsValidDay_ValidDayInJanuary_ReturnsTrue() {
        assertTrue(service.isValidDay(2019, 1, 15));
    }
    @Test
    public void testIsValidDay_LastDayOfMonth_ReturnsTrue() {
        assertTrue(service.isValidDay(2019, 1, 31));
    }
    @Test
    public void testIsValidDay_MonthTooLow_ReturnsFalse() {
        assertFalse(service.isValidDay(2019, 0, 15));
    }
    @Test
    public void testIsValidDay_MonthTooHigh_ReturnsFalse() {
        assertFalse(service.isValidDay(2019, 13, 15));
    }
    @Test
    public void testIsValidDay_DayZero_ReturnsFalse() {
        assertFalse(service.isValidDay(2019, 1, 0));
    }
    @Test
    public void testIsValidDay_Day31InApril_ReturnsFalse() {
        assertFalse(service.isValidDay(2019, 4, 31));
    }
    @Test
    public void testIsValidDay_Day30InFebruary_ReturnsFalse() {
        assertFalse(service.isValidDay(2019, 2, 30));
    }
    @Test
    public void testAddEvent_ValidEvent_CallsRepository() {
        boolean result = service.addEvent(2019, 12, 25, "Christmas");
        
        assertTrue(result);
        verify(mockRepository, times(1)).insertRow(12, 25, "Christmas");
    }
    @Test
    public void testAddEvent_NullEvent_ReturnsFalse() {
        boolean result = service.addEvent(2019, 12, 25, null);
        
        assertFalse(result);
        verify(mockRepository, never()).insertRow(anyInt(), anyInt(), anyString());
    }
    @Test
    public void testAddEvent_EmptyEvent_ReturnsFalse() {
        boolean result = service.addEvent(2019, 12, 25, "   ");
        
        assertFalse(result);
        verify(mockRepository, never()).insertRow(anyInt(), anyInt(), anyString());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsForDay_InvalidDay_ThrowsException() {
        service.getEventsForDay(2019, 13, 10);
    }
    @Test
    public void testCountEvents_EmptyList_ReturnsZero() {
        when(mockRepository.getEvents(2020, 6, 15)).thenReturn(new ArrayList<>());
        
        int count = service.countEvents(2019, 6, 15);
        
        assertEquals(0, count);
    }
    @Test
    public void testHasEvents_DayWithoutEvents_ReturnsFalse() {
        when(mockRepository.getEvents(2020, 3, 20)).thenReturn(new ArrayList<>());
        
        assertFalse(service.hasEvents(2019, 3, 20));
    }
}