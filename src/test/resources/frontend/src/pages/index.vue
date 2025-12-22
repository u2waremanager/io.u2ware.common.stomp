<template>
  <v-app id="inspire">
    <v-app-bar app>
      <router-link to="/">
        <U2wareAvatar></U2wareAvatar>
      </router-link>

      <v-toolbar-title> {{ $t("index.bar.title") }} </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn
        v-if="isAdmin"
        text
        variant="elevated"
        color="error"
        @click="start"
      >
        <v-icon>mdi-account</v-icon> {{ $t("index.bar.start") }}
      </v-btn>
    </v-app-bar>

    <U2wareFooter></U2wareFooter>

    <v-main>
      <v-container class="pa-4" fluid>
        <v-row>
          <v-col cols="4">
            <v-card>
              <v-card-title class="d-flex align-center pe-2">
                <v-icon icon="mdi-cog"></v-icon> &nbsp;
                Actions&nbsp;
              </v-card-title>

              <!-- 
              /////////////////////////////////////////////////////////////////
              //
              /////////////////////////////////////////////////////////////////
              -->
              <!-- {{ connection }} -->
              <v-card-text>
                <v-textarea
                  label="url"
                  placeholder="ws://"
                  variant="outlined"
                  v-model="url"
                  rows="4"
                  :disabled="connection == true"
                ></v-textarea>
                <v-btn
                  block
                  color="primary"
                  v-if="connection == false"
                  @click="connectAction"
                  >Connect</v-btn
                >
                <v-btn
                  block
                  color="error"
                  v-if="connection == true"
                  @click="disconnectAction"
                  >Disonnect
                </v-btn>
              </v-card-text>

              <!-- 
              /////////////////////////////////////////////////////////////////
              //
              /////////////////////////////////////////////////////////////////
              -->
              <!-- {{ subscription }} -->
              <v-card-text>
                <v-text-field
                  v-model="dest"
                  label="channel"
                  placeholder="channel"
                  variant="outlined"
                  v-if="connection == true"
                  :disabled="subscription != undefined"
                ></v-text-field>

                <v-btn
                  block
                  color="primary"
                  v-if="connection == true && subscription == undefined"
                  @click="subscribeAction"
                  >Subscribe</v-btn
                >
                <v-btn
                  block
                  color="error"
                  v-if="connection == true && subscription != undefined"
                  @click="unsubscribeAction"
                  >Unsubscribe</v-btn
                >
              </v-card-text>
              <!-- 
              /////////////////////////////////////////////////////////////////
              //
              /////////////////////////////////////////////////////////////////
              -->
              <v-card-text>
                <v-textarea
                  label="sendMessage"
                  placeholder='{ "key" : value}'
                  variant="outlined"
                  v-model="msg"
                  rows="4"
                  v-if="subscription != undefined"
                ></v-textarea>
                <v-btn
                  block
                  color="primary"
                  v-if="subscription != undefined"
                  @click="sendMessage"
                  >Send</v-btn
                >
              </v-card-text>
            </v-card>
          </v-col>
          <v-col cols="8">
            <v-card>
              <v-card-title class="d-flex align-center pe-2">
                <v-icon icon="mdi-format-list-checkbox"></v-icon> &nbsp;
                Messages&nbsp;
              </v-card-title>

              <!-- {{ this.messages }}  -->

              <v-card-text>
                <v-table style="height: 86vh" fixed-header density="compact">
                  <thead>
                    <tr>
                      <th class="text-left" width="20%">Timestamp</th>
                      <th class="text-left" width="20%">Principal</th>
                      <th class="text-left">Payload</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in messages" :key="item.timestamp">
                      <td>{{ $moment.format(item.timestamp) }}</td>
                      <td>{{ item.principal }}</td>
                      <td>
                        <JsonViewer
                          class="m-0 p-0"
                          :value="item.payload"
                          theme="dark"
                        ></JsonViewer>
                      </td>
                    </tr>
                  </tbody>
                </v-table>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
const x = "[/]";
import $stompjs from "stompjs";
import $contentsApi from "@/assets/apis/contents";

export default {
  data: () => ({
    isAdmin: false,

    url: undefined,
    dest: undefined,
    msg: undefined,

    ws: undefined,
    connection: false,
    subscription: undefined,

    messages: [],
  }),

  computed: {},

  methods: {
    start() {
      $contentsApi.oauth2
        .permission(["ROLE_ADMIN"])
        .then((r) => {
          console.log(x, "start()", 1, r);
          this.$router.push(`/contents`);
        })
        .catch((r) => {
          console.log(x, "start()", 222, r);
          this.$router.push(`/`);
        });
    },

    ///////////////////////////
    //
    ///////////////////////////
    connectAction() {
      console.log(x, "connectAction", this.url);
      this.ws = $stompjs.client(this.url);
      this.ws.connect(
        {},
        (e) => {
          this.connection = true;
          this.subscription = undefined;
        },
        (e) => {
          this.connection = false;
          this.subscription = undefined;
        }
      );
    },
    disconnectAction() {
      console.log(x, "disconnectAction", this.url);
      this.ws.disconnect(
        () => {
          this.connection = false;
          this.subscription = undefined;
        },
        { force: true }
      );
    },

    subscribeAction() {
      console.log(x, "subscribeAction", this.dest);
      this.subscription = this.ws.subscribe(`/topic/${this.dest}`, (m) => {
        this.receivedMessage(m);
      });
    },

    unsubscribeAction() {
      console.log(x, "unsubscribeAction", this.dest);

      if (this.subscription != undefined) {
        this.subscription.unsubscribe();
      }
      this.subscription = undefined;
    },

    ///////////////////////////
    //
    ///////////////////////////
    receivedMessage(msg) {
      console.log(x, "receivedMessage", msg);
      let headers = msg.headers;
      let payload = JSON.parse(msg.body);
      this.messages.push(payload);
    },
    sendMessage() {
      console.log(x, "sendMessage", this.msg);
      let headers = {};
      let payload = undefined;
      try {
        payload = JSON.stringify(JSON.parse(this.msg));
      } catch {
        payload = JSON.stringify(this.msg);
      }
      this.ws.send(`/app/${this.dest}`, headers, payload);
    },
  },

  watch: {},

  mounted() {
    $contentsApi.oauth2
      .permission(["ROLE_ADMIN"])
      .then((r) => {
        console.log(x, "mounted()", 1, r);
        this.isAdmin = true;
      })
      .catch((r) => {
        console.log(x, "mounted()", 222, r);
        this.isAdmin = false;
      });
  },
};
</script>
