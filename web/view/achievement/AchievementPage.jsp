<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="model.Achievement"%>
<%@page import="model.Achievementcategory"%>
<%@page import="model.Badge"%>
<%@page import="model.Achievementreward"%>
<%@page import="model.Treebox"%>
<%@page import="model.Item"%>
<%@page import="model.*"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    EntityManager em = Persistence.createEntityManagerFactory("MizukiForestPU").createEntityManager();
    TreeBoxService tbs = new TreeBoxService(em);
    ItemService is = new ItemService(em);

    List<Achievement> achievementList = (List<Achievement>) session.getAttribute("achievementList");
    List<Achievementcategory> categoryList = (List<Achievementcategory>) session.getAttribute("categoryList");
    List<Badge> badgeList = (List<Badge>) session.getAttribute("badgeList");
    List<Achievementreward> rewardList = (List<Achievementreward>) session.getAttribute("rewardList");
    List<Treebox> treeboxList = (List<Treebox>) session.getAttribute("treeboxList");
    List<Item> itemList = (List<Item>) session.getAttribute("itemList");
    List<Userachievement> userAchievements = (List<Userachievement>) session.getAttribute("userAchievements");

    if (categoryList == null || badgeList == null || achievementList == null || rewardList == null) {
        response.sendRedirect(request.getContextPath() + "/AchievementServlet");
        return;
    };
%>

<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <style>
        .category-card {
            transition: transform 0.3s;
            cursor: pointer;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            height: 100%;
        }
        .category-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
        .card-body{
            background: radial-gradient(circle at top center, #3e76758c, #39917791, #76769e00);
        }
        .badge-img {
            width: 100px;
            height: 100px;
            object-fit: contain;
            margin: 15px auto;
            display: block;
        }
        .achievement-card {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            border-radius: 10px;
        }
        .reward-img {
            width: 50px;
            height: 50px;
            object-fit: contain;
        }
        .reward-quantity {
            position: absolute;
            bottom: 0;
            background-color: rgba(0,0,0,0.7);
            color: white;
            padding: 2px 6px;
            border-radius: 10px;
            font-size: 12px;
        }
        .rewards-row {
            margin-left: -5px;
            margin-right: -5px;
        }
        .rewards-row .col-4 {
            padding-left: 5px;
            padding-right: 5px;
        }
        .achievement-header {
            background-color: #f8f9fa;
            padding: 15px;
            border-bottom: 1px solid #e9ecef;
        }
        .achievement-list {
            display: none;
        }
        .back-btn {
            margin-bottom: 20px;
            display: none;
        }
        .categories-container {
            display: block;
        }
        .hidden-achievement-message {
            background-color: rgba(0,0,0,0.2);
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            margin-bottom: 30px;
        }
    </style>


    <%request.setAttribute("pageTitle", "Achievement");%>
    <jsp:include page="/shared/title.jsp"/>
    <%@ include file="/shared/header.jsp" %>
    <section id="template" class="achievement">

        <%!
            Badge getBadgeForCategory(String badgeId, List<Badge> badgeList) {
                for (Badge badge : badgeList) {
                    if (badge.getBadgeid().equals(badgeId)) {
                        return badge;
                    }
                }
                return null;
            }

            Item getItemById(String itemId, List<Item> itemList) {
                for (Item item : itemList) {
                    if (item.getItemid().equals(itemId)) {
                        return item;
                    }
                }
                return null;
            }

            Treebox getTreeboxById(String treeboxId, List<Treebox> treeboxList) {
                for (Treebox treebox : treeboxList) {
                    if (treebox.getTreeboxid().equals(treeboxId)) {
                        return treebox;
                    }
                }
                return null;
            }

            Date getCompletionDate(String achievementId, List<Userachievement> userAchievements) {
                if (userAchievements != null) {
                    for (Userachievement ua : userAchievements) {
                        if (ua.getUserachievementPK().getAchievementid().equals(achievementId)) {
                            return ua.getDatecompleted();
                        }
                    }
                }
                return null;
            }
        %>

        <div class="container mt-5">
            <h1 class="text-center mb-5">Achievements</h1>

            <button id="backButton" class="btn btn-secondary back-btn">
                <i class="fas fa-arrow-left"></i> Back to Categories
            </button>

            <div class="categories-container">
                <div class="row">
                    <%
                        int count = 0;
                        for (Achievementcategory category : categoryList) {
                            Badge badge = null;
                            if (category.getBadgeid() != null) {
                                for (Badge b : badgeList) {
                                    if (b.getBadgeid().equals(category.getBadgeid().getBadgeid())) {
                                        badge = b;
                                        break;
                                    }
                                }
                            }

                            if (count % 3 == 0 && count > 0) {
                    %></div><div class="row mt-4"><%
                        }
                        count++;
                    %>
                    <div class="col-md-4 mb-4">
                        <div class="category-card" data-category-id="<%= category.getAchievementcategoryid()%>">
                            <div class="card">
                                <div class="card-body text-center">
                                    <% if (badge != null && badge.getBadgeimage() != null) {%>
                                    <img src="${pageContext.request.contextPath}<%= badge.getBadgeimage()%>" alt="<%= category.getAchievementcategoryname()%>" class="badge-img">
                                    <% } else {%>
                                    <img src="images/default-badge.png" alt="<%= category.getAchievementcategoryname()%>" class="badge-img">
                                    <% }%>
                                    <h4 class="card-title"><%= category.getAchievementcategoryname()%></h4>
                                </div>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>

            <% for (Achievementcategory category : categoryList) {
                    List<Achievement> categoryAchievements = new ArrayList<Achievement>();
                    for (Achievement achievement : achievementList) {
                        if (achievement.getAchievementcategoryid() != null
                                && achievement.getAchievementcategoryid().getAchievementcategoryid().equals(category.getAchievementcategoryid())) {
                            categoryAchievements.add(achievement);
                        }
                    }

                    boolean isHiddenCategory = "AC000006".equals(category.getAchievementcategoryid());
                    boolean hasCompletedAchievements = false;

                    if (isHiddenCategory) {
                        for (Achievement achievement : categoryAchievements) {
                            Date completionDate = getCompletionDate(achievement.getAchievementid(), userAchievements);
                            if (completionDate != null) {
                                hasCompletedAchievements = true;
                                break;
                            }
                        }
                    }
            %>
            <div id="category-<%= category.getAchievementcategoryid()%>" class="achievement-list">
                <div class="achievement-header mb-4">
                    <h2 class="text-center"><%= category.getAchievementcategoryname()%> Achievements</h2>
                </div>

                <% if (isHiddenCategory && !hasCompletedAchievements) { %>
                <div class="hidden-achievement-message">
                    <i class="fas fa-lock fa-2x mb-3"></i>
                    <h4>Hidden Achievements</h4>
                    <p>These achievements only appear once you complete them</p>
                </div>
                <% } else {
                    for (Achievement achievement : categoryAchievements) {
                        Date completionDate = getCompletionDate(achievement.getAchievementid(), userAchievements);

                        if (isHiddenCategory && completionDate == null) {
                            continue;
                        }
                %>
                <div class="card achievement-card mb-4">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <h4><%= achievement.getAchievementname()%></h4>
                                <p><%= achievement.getAchievementdescription() != null ? achievement.getAchievementdescription() : "No description available"%></p>
                            </div>
                            <div class="col-md-6">
                                <h5>Rewards:</h5>
                                <div class="row">
                                    <%
                                        List<Achievementreward> achievementRewards = new ArrayList<Achievementreward>();
                                        for (Achievementreward reward : rewardList) {
                                            if (reward.getAchievementid() != null
                                                    && reward.getAchievementid().getAchievementid().equals(achievement.getAchievementid())) {
                                                achievementRewards.add(reward);
                                            }
                                        }

                                        if (achievementRewards.isEmpty()) {
                                    %>
                                    <div class="col-12">No rewards for this achievement.</div>
                                    <% } else {
                                        for (Achievementreward reward : achievementRewards) {
                                    %>
                                    <div class="row rewards-row" style="width:15%">
                                        <div class="col-4 mb-3">
                                            <div class="position-relative">
                                                <% if (reward.getItemid() != null) {
                                                        Item item = getItemById(reward.getItemid().getItemid(), itemList);
                                                        if (item != null) {
                                                %>
                                                <img src="${pageContext.request.contextPath}<%= item.getItemimage() != null ? item.getItemimage() : "images/default-item.png"%>" 
                                                     alt="${pageContext.request.contextPath}<%= item.getItemname()%>" class="reward-img">
                                                <div class="reward-quantity"><%= reward.getQuantity()%></div>
                                                <% }
                                                } else if (reward.getTreeboxid() != null) {
                                                    Treebox treebox = getTreeboxById(reward.getTreeboxid().getTreeboxid(), treeboxList);
                                                    if (treebox != null) {
                                                %>
                                                <img src="${pageContext.request.contextPath}<%= treebox.getTreeboximage() != null ? treebox.getTreeboximage() : "images/default-treebox.png"%>" 
                                                     alt="<%= treebox.getTreeboxname()%>" class="reward-img">
                                                <div class="reward-quantity"><%= reward.getQuantity()%></div>
                                                <% }
                                                    } %>
                                            </div>
                                        </div>
                                    </div>
                                    <% }
                                        } %>
                                </div>
                                <div class="text-end mt-3">
                                    <small class="text-muted">
                                        <% if (completionDate != null) {%>
                                        Completed: <%= new SimpleDateFormat("MMM d, yyyy").format(completionDate)%>
                                        <% } else if (!isHiddenCategory) { %>
                                        Not completed yet
                                        <% } %>
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <% }
                    } %>
            </div>
            <% }%>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            $(document).ready(function () {
                $('.category-card').click(function () {
                    const categoryId = $(this).data('category-id');
                    $('.achievement-list').hide();
                    $('.categories-container').hide();
                    $('.back-btn').show();
                    $('#category-' + categoryId).show();
                });

                $('#backButton').click(function () {
                    $('.achievement-list').hide();
                    $('.back-btn').hide();
                    $('.categories-container').show();
                });
            });
        </script>
    </section>
</html>

<script src="../js/mizukibase.js"></script>