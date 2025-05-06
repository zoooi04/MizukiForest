<%-- 
    Document   : journal-mood
    Created on : Apr 30, 2025, 5:22:14 PM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">

    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/journal-diaries_mood.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
    <section id="journal" class="incsec">
        <%request.setAttribute("pageTitle", "Journal");%>
        <jsp:include page="../../shared/title.jsp"/>
        <%@ include file="../../shared/header.jsp" %>

        <main class="container mt-5 pt-5">
            <%@ page import="java.util.List" %>
            <%@ page import="model.Diaryentry" %>

            <!-- Monthly Mood Chart -->
            <div class="chart-container">
                <h2>MONTHLY MOOD</h2>

                <div class="filter-controls">
                    <div class="filter-group">
                        <p class="filter-label">YEAR:</p>
                        <div class="btn-group" id="year-buttons-monthly">
                            <c:forEach var="yearEntry" items="${moodStats}" varStatus="status">
                                <button class="pixel-btn <c:if test='${status.last}'>active</c:if>" data-year="${yearEntry.key}">${yearEntry.key}</button>
                            </c:forEach>

                        </div>
                    </div>
                </div>

                <div class="chart-wrapper">
                    <canvas id="monthly-mood-chart"></canvas>
                </div>

                <div class="legend">
                    <div class="legend-item">
                        <div class="legend-color" style="background-color: #ffd700;"></div>
                        <span>Excited</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color" style="background-color: #ff7f50;"></div>
                        <span>Happy</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color" style="background-color: #98fb98;"></div>
                        <span>Calm</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color" style="background-color: #add8e6;"></div>
                        <span>Neutral</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color" style="background-color: #87ceeb;"></div>
                        <span>Sad</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color" style="background-color: #9370db;"></div>
                        <span>Frustrated</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color" style="background-color: #ff6347;"></div>
                        <span>Angry</span>
                    </div>
                </div>
            </div>
            <!-- Yearly Mood Chart -->
            <div class="chart-container">
                <h2>YEARLY MOOD OVERVIEW</h2>

                <div class="chart-wrapper">
                    <canvas id="yearly-mood-chart"></canvas>
                </div>
            </div>

        </main>
    </section>
    <script src="../js/mizukibase.js">
    </script>

    <script>
        const moodValues = {
        'Happy': 4,
                'Excited': 3.5,
                'Calm': 3,
                'Neutral': 2.5,
                'Frustrated': 2,
                'Sad': 1.5,
                'Angry': 1
        };
        const moodColors = {
        'Happy': '#ff7f50',
                'Excited': '#ffd700',
                'Calm': '#98fb98',
                'Neutral': '#add8e6',
                'Sad': '#87ceeb',
                'Frustrated': '#9370db',
                'Angry': '#ff6347'
        };
        const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        const moodStats = {
        <c:forEach var="yearEntry" items="${moodStats}" varStatus="yStat">
        "${yearEntry.key}": {
            <c:forEach var="monthEntry" items="${yearEntry.value}" varStatus="mStat">
        "${monthEntry.key}": {
                <c:forEach var="moodCount" items="${monthEntry.value}" varStatus="moStat">
        "${moodCount.key}": ${moodCount.value}<c:if test="${!moStat.last}">,</c:if>
                </c:forEach>
        }<c:if test="${!mStat.last}">,</c:if>
            </c:forEach>
        }<c:if test="${!yStat.last}">,</c:if>
        </c:forEach>
        };
        const allMoodData = {};
        for (const year in moodStats) {
        const months = moodStats[year];
        const monthlyData = [];
        for (const month in months) {
        const moodCounts = months[month];
        let maxCount = 0;
        let selectedMood = 'Neutral';
        for (const mood in moodCounts) {
        if (moodCounts[mood] > maxCount) {
        maxCount = moodCounts[mood];
        selectedMood = mood;
        }
        }
        monthlyData.push({
        monthNum: parseInt(month),
                month: monthNames[parseInt(month) - 1],
                mood: selectedMood,
                moodValue: moodValues[selectedMood] || 2.5
        });
        }
        allMoodData[year] = monthlyData.sort((a, b) => a.monthNum - b.monthNum);
        }

        let monthlyChart, yearlyChart;
        let selectedYear = Object.keys(allMoodData).sort().reverse()[0];
        let selectedMonth = "all"; // default

        console.log('Selected Year:', selectedYear);
        document.addEventListener('DOMContentLoaded', function () {
        initMonthlyChart();
        initYearlyChart();
        document.querySelectorAll('#year-buttons-monthly .pixel-btn').forEach(btn => {
        btn.addEventListener('click', () => {
        const year = btn.getAttribute('data-year');
        if (selectedYear === year) return;
        document.querySelectorAll('#year-buttons-monthly .pixel-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        selectedYear = year;
        updateMonthlyChart();
        updatePeriodLabel();
        })
        });
        updatePeriodLabel();
        });
        function initMonthlyChart() {
        const ctx = document.getElementById('monthly-mood-chart').getContext('2d');
        monthlyChart = new Chart(ctx, {
        type: 'line',
                data: getFilteredMonthlyData(),
                options: getChartOptions(true)
        });
        }

        function updateMonthlyChart() {
        const filtered = getFilteredMonthlyData();
        monthlyChart.data.labels = filtered.labels;
        monthlyChart.data.datasets[0].data = filtered.data;
        monthlyChart.update();
        }

        function getFilteredMonthlyData() {
        const months = allMoodData[selectedYear] || [];
        const filteredMonths = selectedMonth === "all"
                ? months
                : months.filter(m => m.monthNum === parseInt(selectedMonth));
        return {
        labels: filteredMonths.map(m => m.month),
                datasets: [{
                label: 'Mood',
                        data: filteredMonths.map(m => m.moodValue),
                        backgroundColor: '#ff7f50',
                        borderColor: '#ff7f50',
                        borderWidth: 2,
                        pointRadius: 6,
                        pointBackgroundColor: '#ff7f50',
                        pointBorderColor: '#333',
                        pointBorderWidth: 1,
                        pointHoverRadius: 8,
                        tension: 0.1
                }]
        };
        }

        function updatePeriodLabel() {
        const monthText = selectedMonth === 'all' ? 'ALL MONTHS' : monthNames[parseInt(selectedMonth) - 1].toUpperCase();
        document.getElementById('selected-period-display').textContent = `( ${selectedYear} - ${monthText} )`;
        }

        function processYearlyData() {
        return Object.keys(allMoodData).map(year => {
        const months = allMoodData[year];
        let total = 0;
        months.forEach(m => total += m.moodValue);
        const avg = total / months.length;
        const moodFreq = {};
        months.forEach(m => moodFreq[m.mood] = (moodFreq[m.mood] || 0) + 1);
        const dominant = Object.entries(moodFreq).sort((a, b) => b[1] - a[1])[0][0];
        return { year, averageMoodValue: avg, dominantMood: dominant };
        });
        }

        function initYearlyChart() {
        const ctx = document.getElementById('yearly-mood-chart').getContext('2d');
        const yearlyData = processYearlyData();
        yearlyChart = new Chart(ctx, {
        type: 'line',
                data: {
                labels: yearlyData.map(y => y.year),
                        datasets: [{
                        label: 'Average Mood',
                                data: yearlyData.map(y => y.averageMoodValue),
                                backgroundColor: '#add8e6',
                                borderColor: '#add8e6',
                                borderWidth: 2,
                                pointRadius: 6,
                                pointBackgroundColor: '#add8e6',
                                pointBorderColor: '#333',
                                pointBorderWidth: 1,
                                pointHoverRadius: 8,
                                tension: 0.1
                        }]
                },
                options: getChartOptions(false)
        });
        }

        function getChartOptions(isMonthly) {
        return {
        responsive: true,
                maintainAspectRatio: false,
                scales: {
                y: {
                min: 0.5,
                        max: 4.5,
                        ticks: {
                        stepSize: 0.5,
                                callback: function (value) {
                                const moodLabelMap = {
                                1: 'Angry',
                                        1.5: 'Sad',
                                        2: 'Frustrated',
                                        2.5: 'Neutral',
                                        3: 'Calm',
                                        3.5: 'Excited',
                                        4: 'Happy'
                                };
                                return moodLabelMap[value] || '';
                                },
                                font: {
                                family: 'monospace',
                                        size: 12
                                },
                                color: '#fff'
                        },
                        grid: {
                        color: '#ddd'
                        }
                },
                        x: {
                        ticks: {
                        font: {
                        family: 'monospace',
                                size: 12
                        },
                                color: '#fff'
                        },
                                grid: {
                                color: '#ddd'
                                }
                        }
                },
                plugins: {
                tooltip: {
                callbacks: {
                label: function (context) {
                const labels = {
                1: 'Angry',
                        1.5: 'Sad',
                        2: 'Frustrated',
                        2.5: 'Neutral',
                        3: 'Calm',
                        3.5: 'Excited',
                        4: 'Happy'
                };
                return labels[context.raw] || '';
                }
                },
                        backgroundColor: '#fff',
                        titleColor: '#333',
                        bodyColor: '#333',
                        borderColor: '#333',
                        borderWidth: 1,
                        cornerRadius: 0,
                        displayColors: false,
                        titleFont: {
                        family: 'monospace'
                        },
                        bodyFont: {
                        family: 'monospace'
                        }
                },
                        legend: {
                        display: false
                        }
                }
        };
        }
    </script>


</html>
