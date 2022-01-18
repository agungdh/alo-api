package com.bpkad.api.alo.repo;

import com.bpkad.api.alo.helper.AdhHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component("SpmRepo")
public class SpmRepo {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Map<String, Object>> cariSpmUntukPenerimaan(String nospm) {
        String query = "select top 5 tspm.* , (\n" +
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
                "and tspm.kd_sub = rsu.kd_sub\n" +
                "where tspm.no_spm like :nospm";

        nospm = "%" + nospm + "%";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("nospm", nospm);

        List<Map<String, Object>> datas = namedParameterJdbcTemplate.queryForList(query, namedParameters);

        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> data = datas.get(i);

            data.put("nilai", AdhHelper.moneyNullToZero((BigDecimal) data.get("nilai")));
            data.put("pot", AdhHelper.moneyNullToZero((BigDecimal) data.get("pot")));
            data.put("info", AdhHelper.moneyNullToZero((BigDecimal) data.get("info")));

            BigDecimal nilai = (BigDecimal) data.get("nilai");
            BigDecimal pot = (BigDecimal) data.get("pot");
            BigDecimal total = nilai.subtract(pot);

            data.put("total", total);
        }

        return datas;
    }
}
