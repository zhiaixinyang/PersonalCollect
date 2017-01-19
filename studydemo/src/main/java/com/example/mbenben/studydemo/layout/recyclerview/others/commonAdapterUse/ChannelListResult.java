package com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse;

import java.util.List;

/**
 * Created by Darren on 2016/12/27.
 * Email: 240336124@qq.com
 * Description:
 */

public class ChannelListResult {

    private String message;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private RotateBannerBean rotate_banner;
        private CategoriesBean categories;

        public RotateBannerBean getRotate_banner() {
            return rotate_banner;
        }

        public void setRotate_banner(RotateBannerBean rotate_banner) {
            this.rotate_banner = rotate_banner;
        }

        public CategoriesBean getCategories() {
            return categories;
        }

        public void setCategories(CategoriesBean categories) {
            this.categories = categories;
        }

        public static class RotateBannerBean {
            /**
             * count : 0
             * banners : []
             */

            private int count;
            private List<BannerBean> banners;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<BannerBean> getBanners() {
                return banners;
            }

            public void setBanners(List<BannerBean> banners) {
                this.banners = banners;
            }
        }


        public static class BannerBean {
            public Banner banner_url;
        }

        public static class Banner {
            public String title;
            public List<BannerUrl> url_list;
        }

        public class BannerUrl {
            public String url;
        }

        public static class CategoriesBean {

            private String name;
            private int priority;
            private int category_count;
            private String intro;
            private int id;
            private Object icon;
            private List<CategoryListBean> category_list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public int getCategory_count() {
                return category_count;
            }

            public void setCategory_count(int category_count) {
                this.category_count = category_count;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getIcon() {
                return icon;
            }

            public void setIcon(Object icon) {
                this.icon = icon;
            }

            public List<CategoryListBean> getCategory_list() {
                return category_list;
            }

            public void setCategory_list(List<CategoryListBean> category_list) {
                this.category_list = category_list;
            }

            public static class CategoryListBean {

                private String extra;
                private int allow_video;
                private boolean is_recommend;
                private int allow_text_and_pic;
                private String channels;
                private boolean visible;
                private String intro;
                private String top_end_time;
                private int id;
                private String icon_url;
                private String share_url;
                private String buttons;
                private boolean is_top;
                private boolean is_risk;
                private int allow_multi_image;
                private String placeholder;
                private String icon;
                private int total_updates;
                private int subscribe_count;
                private String name;
                private boolean has_timeliness;
                private String tag;
                private String small_icon_url;
                private String small_icon;
                private String top_start_time;
                private int priority;
                private List<?> material_bar;

                public String getExtra() {
                    return extra;
                }

                public void setExtra(String extra) {
                    this.extra = extra;
                }

                public int getAllow_video() {
                    return allow_video;
                }

                public void setAllow_video(int allow_video) {
                    this.allow_video = allow_video;
                }

                public boolean isIs_recommend() {
                    return is_recommend;
                }

                public void setIs_recommend(boolean is_recommend) {
                    this.is_recommend = is_recommend;
                }

                public int getAllow_text_and_pic() {
                    return allow_text_and_pic;
                }

                public void setAllow_text_and_pic(int allow_text_and_pic) {
                    this.allow_text_and_pic = allow_text_and_pic;
                }

                public String getChannels() {
                    return channels;
                }

                public void setChannels(String channels) {
                    this.channels = channels;
                }

                public boolean isVisible() {
                    return visible;
                }

                public void setVisible(boolean visible) {
                    this.visible = visible;
                }

                public String getIntro() {
                    return intro;
                }

                public void setIntro(String intro) {
                    this.intro = intro;
                }

                public String getTop_end_time() {
                    return top_end_time;
                }

                public void setTop_end_time(String top_end_time) {
                    this.top_end_time = top_end_time;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIcon_url() {
                    return icon_url;
                }

                public void setIcon_url(String icon_url) {
                    this.icon_url = icon_url;
                }

                public String getShare_url() {
                    return share_url;
                }

                public void setShare_url(String share_url) {
                    this.share_url = share_url;
                }

                public String getButtons() {
                    return buttons;
                }

                public void setButtons(String buttons) {
                    this.buttons = buttons;
                }

                public boolean isIs_top() {
                    return is_top;
                }

                public void setIs_top(boolean is_top) {
                    this.is_top = is_top;
                }

                public boolean isIs_risk() {
                    return is_risk;
                }

                public void setIs_risk(boolean is_risk) {
                    this.is_risk = is_risk;
                }

                public int getAllow_multi_image() {
                    return allow_multi_image;
                }

                public void setAllow_multi_image(int allow_multi_image) {
                    this.allow_multi_image = allow_multi_image;
                }

                public String getPlaceholder() {
                    return placeholder;
                }

                public void setPlaceholder(String placeholder) {
                    this.placeholder = placeholder;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public int getTotal_updates() {
                    return total_updates;
                }

                public void setTotal_updates(int total_updates) {
                    this.total_updates = total_updates;
                }

                public int getSubscribe_count() {
                    return subscribe_count;
                }

                public void setSubscribe_count(int subscribe_count) {
                    this.subscribe_count = subscribe_count;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public boolean isHas_timeliness() {
                    return has_timeliness;
                }

                public void setHas_timeliness(boolean has_timeliness) {
                    this.has_timeliness = has_timeliness;
                }

                public String getTag() {
                    return tag;
                }

                public void setTag(String tag) {
                    this.tag = tag;
                }

                public String getSmall_icon_url() {
                    return small_icon_url;
                }

                public void setSmall_icon_url(String small_icon_url) {
                    this.small_icon_url = small_icon_url;
                }

                public String getSmall_icon() {
                    return small_icon;
                }

                public void setSmall_icon(String small_icon) {
                    this.small_icon = small_icon;
                }

                public String getTop_start_time() {
                    return top_start_time;
                }

                public void setTop_start_time(String top_start_time) {
                    this.top_start_time = top_start_time;
                }

                public int getPriority() {
                    return priority;
                }

                public void setPriority(int priority) {
                    this.priority = priority;
                }

                public List<?> getMaterial_bar() {
                    return material_bar;
                }

                public void setMaterial_bar(List<?> material_bar) {
                    this.material_bar = material_bar;
                }
            }
        }
    }
}
