<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Growth Chart</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<h2>New Customers in Last 6 Months</h2>
<div style="background:white; padding:20px; border-radius:16px; max-width:700px;">
    <canvas id="customerChart" width="700" height="300"></canvas>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    fetch('Customer?action=monthlyChartData')
        .then(res => res.json())
        .then(data => {
            const labels = data.map(item => item.month);
            const counts = data.map(item => item.count);

            const ctx = document.getElementById('customerChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'New Customers',
                        data: counts,
                        fill: true,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        tension: 0.3,
                        pointRadius: 5,
                        pointBackgroundColor: 'rgba(75, 192, 192, 1)'
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { display: true, position: 'top' },
                        title: { display: true, text: 'Customer Growth (Last 6 Months)' }
                    },
                    scales: {
                        y: { beginAtZero: true, title: { display:true, text:'Customers' } },
                        x: { title: { display:true, text:'Month' } }
                    }
                }
            });
        })
        .catch(err => console.error('Error fetching chart data:', err));
});
</script>
</body>
</html>