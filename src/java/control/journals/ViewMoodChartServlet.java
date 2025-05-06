package control.journals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Diaryentry;
import model.Users;
import model.journalService.DiaryentryService;

/**
 *
 * @author JiaQuann
 */
@WebServlet(name = "ViewMoodChartServlet", urlPatterns = {"/ViewMoodChartServlet"})
public class ViewMoodChartServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager mgr;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("isSearch") != null) {
                session.removeAttribute("isSearch");
            }

            Users user = (Users) session.getAttribute("user");
            // if not enter back to login
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            String userId = user.getUserid();
            DiaryentryService diaryService = new DiaryentryService(mgr);

            // 获取未删除的日记
            List<Diaryentry> allEntries = diaryService.findAllDiaryentries();
            List<Diaryentry> activeDiaryEntries = new ArrayList<>();
            for (Diaryentry entry : allEntries) {
                if (!entry.getIsdeleted()) {
                    activeDiaryEntries.add(entry);
                }
            }

            session.setAttribute("diaryEntries", activeDiaryEntries);

            // 获取每月情绪统计
            List<Object[]> moodStats = diaryService.getMonthlyMoodStatsByUserId(userId);
            Map<String, Map<Integer, Map<String, Integer>>> yearMonthMoodMap = new LinkedHashMap<>();

            // 处理 moodStats 并将其组织为按年份和月份分组的 Map
            for (Object[] row : moodStats) {
                int year = (Integer) row[0];
                int month = (Integer) row[1];
                String mood = (String) row[2];
                Long count = (Long) row[3];

                // 创建年份和月份格式字符串
                String yearKey = String.format("%04d", year);

                // 确保年份存在
                yearMonthMoodMap.putIfAbsent(yearKey, new HashMap<>());

                // 获取当前年份下的月份映射
                Map<Integer, Map<String, Integer>> monthMap = yearMonthMoodMap.get(yearKey);

                // 确保月份存在
                monthMap.putIfAbsent(month, new HashMap<>());

                // 获取当前月份的情绪统计
                Map<String, Integer> moodCountMap = monthMap.get(month);

                // 更新当前情绪的计数
                moodCountMap.put(mood, moodCountMap.getOrDefault(mood, 0) + count.intValue());
            }

            // 将构建好的数据存到 session 中以便在 JSP 中显示
            session.setAttribute("moodStats", yearMonthMoodMap);

            // 重定向到前端页面
            response.sendRedirect(request.getContextPath() + "/view/journals/journal-mood.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to generate mood chart data.");
        }
    }
}
