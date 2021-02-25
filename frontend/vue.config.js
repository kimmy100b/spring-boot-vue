module.exports = {
    outputDir: "../src/main/resources/static",  // 빌드 타겟 디렉토리
    devServer: {
        proxy: {
            '/api': {
                // '/api' 로 들어오면 포트 8081(스프링 서버)로 보낸다
                // 윤정 - '/api' 로 들어오면 포트 8080(스프링 서버)로 보낸다
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true
            }
        }
    }
};
