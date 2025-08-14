<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahanaedu.model.ProductSales" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Top Selling Products</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <h2>Top Selling Products</h2>
    <canvas id="topProductsChart" width="600" height="400"></canvas>

    <script>
        const labels = [
            <% 
                List<ProductSales> topProducts = (List<ProductSales>) request.getAttribute("topProducts");
                for (int i = 0; i < topProducts.size(); i++) {
                    out.print("'" + topProducts.get(i).getProductName() + "'");
                    if (i < topProducts.size() - 1) out.print(",");
                }
            %>
        ];

        const data = [
            <% 
                for (int i = 0; i < topProducts.size(); i++) {
                    out.print(topProducts.get(i).getQuantitySold());
                    if (i < topProducts.size() - 1) out.print(",");
                }
            %>
        ];

        const ctx = document.getElementById('topProductsChart').getContext('2d');
        const topProductsChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Units Sold',
                    data: data,
                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: { stepSize: 10 }
                    }
                },
                plugins: {
                    legend: { display: true, position: 'top' },
                    title: { display: true, text: 'Top Selling Products' }
                }
            }
        });
    </script>
</body>
</html>