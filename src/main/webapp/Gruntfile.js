module.exports = function (grunt) {

    // Project configuration.
    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        concat: {
            js: {
                src: ['resources/app.js', 'resources/js/**/**/*.js'],
                dest: 'build/js/scripts.js'
            },
            css: {
                src: ['resources/css/**/*.s+(a|c)ss'],
                dest: 'build/css/styles.scss'
            },
            html: {
                src: ['resources/js/**/**/*.html', 'index.html'],
                dest: 'build/app/main.html'
            },
        },
        eslint: {
            target: ['resources/js/app.js']
        },
        sasslint: {
            options: {
                configFile: '.sass-lint.yml',
            },
            target: ['resources/**/*.s+(a|c)ss']
        },
        watch: {
            js: {
                files: ['resources/js/**/*.js'],
                tasks: ['concat:js', 'eslint'],
                options: {
                    livereload: true,
                }
            },
            css: {
                files: ['resources/css/**/*.s+(a|c)ss'],
                tasks: ['concat:css', 'sasslint'],
                options: {
                    livereload: true,
                }
            },
            html: {
                files: ['resources/js/**/**/*.html', 'index.html'],
                tasks: ['concat:html'],
                options: {
                    livereload: true,
                }
            },
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks("grunt-eslint");
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-sass-lint');
    grunt.registerTask('default', ['concat','watch']);
};