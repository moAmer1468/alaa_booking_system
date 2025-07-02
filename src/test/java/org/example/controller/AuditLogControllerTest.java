package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuditLogController.class)
class AuditLogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private org.example.service.AuditLogService auditLogService;

    @Test
    void testGetAllAuditLogs() throws Exception {
        mockMvc.perform(get("/audit-logs"))
                .andExpect(status().isOk());
    }
}

