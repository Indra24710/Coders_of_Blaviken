const routerBase =
  process.env.DEPLOY_ENV === 'GH_PAGES'
    ? {
        router: {
          base: '/jatayu/',
          middleware: 'i18n',
        },
      }
    : {};

export default {
  mode: 'spa',
  generate: {
    fallback: true,
    routes: ['/', '/about', '/fr', '/fr/about'],
  },
  /*
   ** Headers of the page
   */
  head: {
    title: 'Jatayu™',
    meta: [
      {charset: 'utf-8'},
      {name: 'viewport', content: 'width=device-width, initial-scale=1'},
      {
        hid: 'description',
        name: 'description',
        content: process.env.npm_package_description || '',
      },
      {
        'http-equiv': 'Content-Security-Policy',
        content: 'upgrade-insecure-requests',
      },
    ],
    link: [
      {
        rel: 'stylesheet',
        href: 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css',
      },
      {rel: 'icon', type: 'image/x-icon', href: '/favicon.png'},
    ],
  },

  ...routerBase,

  /*
   ** Global CSS
   */
  css: ['~/assets/scss/style.scss'],
  /*
   ** Plugins to load before mounting the App
   */
  plugins: [
    '@/plugins/medium-zoom.js',
    {
      src: '~/plugins/chart.js',
      mode: 'client',
    },
    '@/plugins/firebase.js',
    '@/plugins/fireauth.js',
    '@/plugins/i18n.js',
  ],

  env: {
    apiKey: process.env.API_KEY,
    authDomain: process.env.AUTH_DOMAIN,
    databaseURL: process.env.DATABASE_URL,
    projectId: process.env.PROJECT_ID,
    storageBucket: process.env.STORAGE_BUCKET,
    messagingSenderId: process.env.MESSAGING_SENDER_ID,
    appId: process.env.APP_ID,
    measurementId: process.env.MEASUREMENT_ID,
  },
  /*
   ** Nuxt.js dev-modules
   */
  script: [{src: 'https://kit.fontawesome.com/145ca0f25f.js'}, {src: '~/assets/js/active.js'}],
  buildModules: ['@nuxtjs/dotenv'],
  /*
   ** Nuxt.js modules
   */
  modules: [
    '@nuxtjs/pwa',
    '@nuxtjs/axios',
    'nuxt-buefy',
    [
      'nuxt-gmaps',
      {
        key: process.env.GMAPS_API_KEY,
      },
    ],
    // [
    //   'nuxt-i18n',
    //   {
    //     locales: [
    //       {
    //         name: 'Hindi',
    //         code: 'hi',
    //         iso: 'hi',
    //         file: 'hi.js',
    //       },
    //       {
    //         name: 'English',
    //         code: 'en',
    //         iso: 'en-US',
    //         file: 'en-US.js',
    //       },
    //     ],
    //     langDir: 'lang/',
    //     defaultLocale: 'it',
    //   },
    // ],
  ],
  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    baseURL: '',
  },
  /*
   ** Build configuration
   */
  build: {
    /*
     ** You can extend webpack config here
     */
    extend(config, ctx) {},
    postcss: {
      preset: {
        features: {
          customProperties: false,
        },
      },
    },
    vendor: ['vue-i18n'],
  },

  pwa: {
    manifest: {
      name: 'Jatayu | Criminal Detection and Tracking',
      short_name: 'Jatayu',
      lang: 'en',
      display: 'standalone',
      theme_color: '#ffffff',
    },
  },
};
