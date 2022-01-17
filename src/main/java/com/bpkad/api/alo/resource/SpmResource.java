package com.bpkad.api.alo.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/spm")
public class SpmResource {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpmResource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    final static String querySpm = "select tspm.* , (\n" +
            "select sum(tspmr.nilai)\n" +
            "from KEU2903_2021.dbo.ta_spm_rinc as tspmr\n" +
            "where tspm.tahun = tspmr.tahun\n" +
            "and tspm.no_spm = tspmr.no_spm\n" +
            ") as nilai, (\n" +
            "select sum(tspmp.nilai)\n" +
            "from KEU2903_2021.dbo.ta_spm_pot as tspmp\n" +
            "where tspm.tahun = tspmp.tahun\n" +
            "and tspm.no_spm = tspmp.no_spm\n" +
            ") as pot, (\n" +
            "select sum(tspmi.nilai)\n" +
            "from KEU2903_2021.dbo.ta_spm_info as tspmi\n" +
            "where tspm.tahun = tspmi.tahun\n" +
            "and tspm.no_spm = tspmi.no_spm\n" +
            ") as info,\n" +
            "rur.nm_urusan, rb.nm_bidang, ru.nm_unit, rsu.nm_sub_unit\n" +
            "from KEU2903_2021.dbo.ta_spm as tspm\n" +
            "join KEU2903_2021.dbo.ref_urusan as rur\n" +
            "on tspm.kd_urusan = rur.kd_urusan\n" +
            "join KEU2903_2021.dbo.ref_bidang as rb\n" +
            "on tspm.kd_urusan = rb.kd_urusan\n" +
            "and tspm.kd_bidang = rb.kd_bidang\n" +
            "join KEU2903_2021.dbo.ref_unit as ru\n" +
            "on tspm.kd_urusan = ru.kd_urusan\n" +
            "and tspm.kd_bidang = ru.kd_bidang\n" +
            "and tspm.kd_unit = ru.kd_unit\n" +
            "join KEU2903_2021.dbo.ref_sub_unit as rsu\n" +
            "on tspm.kd_urusan = rsu.kd_urusan\n" +
            "and tspm.kd_bidang = rsu.kd_bidang\n" +
            "and tspm.kd_unit = rsu.kd_unit\n" +
            "and tspm.kd_sub = rsu.kd_sub";

    @GetMapping("/info")
    public ResponseEntity<List<Map<String, Object>>> info() {
        return ResponseEntity.ok().body(jdbcTemplate.queryForList(querySpm));
    }
}
